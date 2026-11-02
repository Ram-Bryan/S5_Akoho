/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.fileupload.FileItem
 */
package importUtils.fileItemParser;

import importUtils.fileItemParser.ISTtranslaterEnum;
import importUtils.inputStreamTranslater.InputStreamTranslater;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

public class FileItemParser {
    private static final HashMap<String, InputStreamTranslater> CONTEXT;

    public List<HashMap<String, String>> parse(FileItem fileItem, String[] fields, boolean bool) throws Exception {
        String fileName = fileItem.getName();
        System.out.println("filename " + fileName);
        String extension = fileName.split("\\.")[fileName.split("\\.").length - 1];
        System.out.println("extension " + extension);
        InputStreamTranslater translater = CONTEXT.get(extension);
        return translater.translate(fileItem.getInputStream(), fields, bool);
    }

    static {
        ISTtranslaterEnum[] map;
        CONTEXT = new HashMap();
        for (ISTtranslaterEnum state : map = ISTtranslaterEnum.values()) {
            CONTEXT.put(state.type(), state.parser());
        }
    }
}

