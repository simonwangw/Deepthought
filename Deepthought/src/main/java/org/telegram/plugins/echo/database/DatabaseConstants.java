package org.telegram.plugins.echo.database;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 16 of October of 2016
 */
class DatabaseConstants {
    static final String controllerDB = "com.mysql.cj.jdbc.Driver";
    static final String userDB = "tguser";
    static final String password = "IvM@ck#z9$Eqy3aGB";
    private static final String databaseName = "tg_local";
    static final String linkDB = "jdbc:mysql://rm-rj9359u6ma681ncgto.mysql.rds.aliyuncs.com:3306/" + databaseName
            + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";

}