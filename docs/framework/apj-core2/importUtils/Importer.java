/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  org.apache.commons.fileupload.FileItem
 */
package importUtils;

import bean.ClassMAPTable;
import importUtils.fileItemParser.FileItemParser;
import importUtils.importable.Importable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

public class Importer {
    public void importFile(Importable importable, FileItem fileItem, String refUser, Connection connection) throws Exception {
        FileItemParser parser = new FileItemParser();
        String[] fields = importable.importedFields();
        List<HashMap<String, String>> list = parser.parse(fileItem, fields, importable.withHeader());
        for (int i = 0; i < list.size(); ++i) {
            Importable imp = importable.duplicate();
            imp.buildSelf(list.get(i), connection);
            ClassMAPTable cmt = (ClassMAPTable)imp;
            cmt.createObject(refUser, connection);
        }
    }
}

