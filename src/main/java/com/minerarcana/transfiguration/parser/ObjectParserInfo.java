package com.minerarcana.transfiguration.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ObjectParserInfo<T extends IObjectBuilder<U>, U, V> {
    private final String fieldName;
    private final BiFunction<T, V, T> withBuilder;
    private final Function<U, V> getValue;
    private final IObjectParser<V> objectParser;
    private final boolean required;

    public ObjectParserInfo(String fieldName, BiFunction<T, V, T> withBuilder, Function<U, V> getValue, IObjectParser<V> objectParser) {
        this(fieldName, withBuilder, getValue, objectParser, true);
    }

    public ObjectParserInfo(String fieldName, BiFunction<T, V, T> withBuilder, Function<U, V> getValue, IObjectParser<V> objectParser, boolean required) {
        this.fieldName = fieldName;
        this.withBuilder = withBuilder;
        this.getValue = getValue;
        this.objectParser = objectParser;
        this.required = required;
    }

    public String getFieldName() {
        return fieldName;
    }

    public BiFunction<T, V, T> getWithBuilder() {
        return withBuilder;
    }

    public boolean isRequired() {
        return this.required;
    }

    public Function<U, V> getGetValue() {
        return getValue;
    }

    public IObjectParser<V> getObjectParser() {
        return objectParser;
    }

    public T fromJson(T objectBuilder, JsonObject parent) throws JsonParseException {
        JsonElement jsonElement = parent.get(this.getFieldName());
        if (this.isRequired() && jsonElement == null) {
            throw new JsonParseException("No Element found for field '" + fieldName + "'");
        } else if (jsonElement != null) {
            return this.getWithBuilder().apply(objectBuilder, this.getObjectParser().fromJson(jsonElement));
        } else {
            return objectBuilder;
        }
    }

    public JsonElement toJson(U parent) {
        return this.getObjectParser().toJson(this.getGetValue().apply(parent));
    }
}
