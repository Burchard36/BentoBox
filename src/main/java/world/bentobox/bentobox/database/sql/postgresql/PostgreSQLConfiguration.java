package world.bentobox.bentobox.database.sql.postgresql;

import world.bentobox.bentobox.database.sql.SQLConfiguration;

/**
 * Contains PostgreSQL strings for the database.
 * They need to be slightly adapted from MySQL's one because of some Postgre query specifications.
 * @author Poslovitch
 * @since 1.10.0
 */
public class PostgreSQLConfiguration extends SQLConfiguration {
    /**
     * @param canonicalName - canonical name of the class being stored.
     */
    public PostgreSQLConfiguration(String canonicalName) {
        super(canonicalName);
        super.schema("CREATE TABLE IF NOT EXISTS \"" + canonicalName +
                "\" (json JSON, uniqueId VARCHAR(255) GENERATED ALWAYS AS (json->\"$.uniqueId\"), UNIQUE INDEX i (uniqueId) )");
        super.loadObjects("SELECT `json` FROM \"" + canonicalName + "\"");
        super.loadObject("SELECT `json` FROM \"" + canonicalName + "\" WHERE uniqueId = ? LIMIT 1");
        super.saveObject("INSERT INTO \"" + canonicalName + "\" (json) VALUES (?) ON DUPLICATE KEY UPDATE json = ?");
        super.deleteObject("DELETE FROM \"" + canonicalName + "\" WHERE uniqueId = ?");
        super.objectExists("SELECT IF ( EXISTS( SELECT * FROM \"" + canonicalName + "\" WHERE `uniqueId` = ?), 1, 0)");
    }
}
