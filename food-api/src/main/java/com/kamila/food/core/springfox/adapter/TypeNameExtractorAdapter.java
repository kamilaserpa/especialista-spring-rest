package com.kamila.food.core.springfox.adapter;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedArrayType;
import com.fasterxml.classmate.types.ResolvedObjectType;
import com.fasterxml.classmate.types.ResolvedPrimitiveType;
import com.google.common.base.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.schema.GenericTypeNamingStrategy;
import springfox.documentation.spi.schema.TypeNameProviderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;

import java.lang.reflect.Type;

@Primary
@Component
public class TypeNameExtractorAdapter extends TypeNameExtractor {

    private final TypeResolver typeResolver;
    private final PluginRegistry<TypeNameProviderPlugin, DocumentationType> typeNameProviders;
    private final EnumTypeDeterminer enumTypeDeterminer;

    public TypeNameExtractorAdapter(TypeResolver typeResolver,
                                    PluginRegistry<TypeNameProviderPlugin, DocumentationType> typeNameProviders,
                                    EnumTypeDeterminer enumTypeDeterminer) {
        super(typeResolver, typeNameProviders, enumTypeDeterminer);

        this.typeResolver = typeResolver;
        this.typeNameProviders = typeNameProviders;
        this.enumTypeDeterminer = enumTypeDeterminer;
    }

    public String typeName(ModelContext context) {
        ResolvedType type = this.asResolved(context.getType());
        return Collections.isContainerType(type) ? Collections.containerType(type) : this.innerTypeName(type, context);
    }

    private ResolvedType asResolved(Type type) {
        return this.typeResolver.resolve(type, new Type[0]);
    }

    private String genericTypeName(ResolvedType resolvedType, ModelContext context) {
        Class<?> erasedType = resolvedType.getErasedType();
        GenericTypeNamingStrategy namingStrategy = context.getGenericNamingStrategy();
        ModelNameContext nameContext = new ModelNameContext(resolvedType.getErasedType(), context.getDocumentationType());
        String simpleName = (String) Optional.fromNullable(Types.typeNameFor(erasedType)).or(this.typeName(nameContext));
        StringBuilder sb = new StringBuilder(String.format("%s%s", simpleName, namingStrategy.getOpenGeneric()));
        boolean first = true;

        for (int index = 0; index < erasedType.getTypeParameters().length; ++index) {
            ResolvedType typeParam = (ResolvedType) resolvedType.getTypeParameters().get(index);
            if (first) {
                sb.append(this.innerTypeName(typeParam, context));
                first = false;
            } else {
                sb.append(String.format("%s%s", namingStrategy.getTypeListDelimiter(), this.innerTypeName(typeParam, context)));
            }
        }

        sb.append(namingStrategy.getCloseGeneric());
        return sb.toString();
    }

    private String innerTypeName(ResolvedType type, ModelContext context) {
        return type.getTypeParameters().size() > 0 && type.getErasedType().getTypeParameters().length > 0 ? this.genericTypeName(type, context) : this.simpleTypeName(type, context);
    }

    private String simpleTypeName(ResolvedType type, ModelContext context) {
        Class<?> erasedType = type.getErasedType();
        if (type instanceof ResolvedPrimitiveType) {
            return Types.typeNameFor(erasedType);
        } else if (this.enumTypeDeterminer.isEnum(erasedType)) {
            return "string";
        } else if (type instanceof ResolvedArrayType) {
            GenericTypeNamingStrategy namingStrategy = context.getGenericNamingStrategy();
            return String.format("Array%s%s%s", namingStrategy.getOpenGeneric(), this.simpleTypeName(type.getArrayElementType(), context), namingStrategy.getCloseGeneric());
        } else {
            if (type instanceof ResolvedObjectType) {
                String typeName = Types.typeNameFor(erasedType);
                if (typeName != null) {
                    return typeName;
                }
            }

            return this.typeName(new ModelNameContext(type.getErasedType(), context.getDocumentationType()));
        }
    }

    // Alterando 'getPluginFor' para 'getPluginOrDefaultFor'
    private String typeName(ModelNameContext context) {
        TypeNameProviderPlugin selected = (TypeNameProviderPlugin) this.typeNameProviders.getPluginOrDefaultFor(context.getDocumentationType(), new DefaultTypeNameProvider());
        return selected.nameFor(context.getType());
    }

}
