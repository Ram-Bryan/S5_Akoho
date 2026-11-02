/*
 * Decompiled with CFR 0.152.
 */
package importUtils.fileItemParser;

import importUtils.inputStreamTranslater.InputStreamTranslater;
import importUtils.inputStreamTranslater.imp.XlsTranslater;

public enum ISTtranslaterEnum {
    XLS("xls", new XlsTranslater()),
    XLSX("xlsx", new XlsTranslater());

    private final String type;
    private final InputStreamTranslater parser;

    private ISTtranslaterEnum(String type, InputStreamTranslater parser) {
        this.type = type;
        this.parser = parser;
    }

    public InputStreamTranslater parser() {
        return this.parser;
    }

    public String type() {
        return this.type;
    }
}

