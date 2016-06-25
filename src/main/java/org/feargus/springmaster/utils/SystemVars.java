package org.feargus.springmaster.utils;

public class SystemVars {
    private static SystemVars instance = null;

    private String DB_URL = null;
    private String ROOT_URL = null;
    private String ADMIN_EMAIL = null;
    private String ADMIN_EMAIL_PSWRD = null;
    private String SYSTEM_BUILD = null;

    protected SystemVars() {
	this.DB_URL = System.getenv("DATABASE_URL");
	this.ROOT_URL = System.getenv("ROOT_URL");
	this.ADMIN_EMAIL = System.getenv("ADMIN_EMAIL");
	this.ADMIN_EMAIL_PSWRD = System.getenv("ADMIN_EMAIL_PSWRD");
	this.SYSTEM_BUILD = System.getenv("SPRING_BUILD_LEVEL");
    }

    public static SystemVars getInstance() {
	if (instance == null) {
	    instance = new SystemVars();
	}
	return instance;
    }

    public String getDB_URL() {
	return DB_URL;
    }

    public String getROOT_URL() {
	return ROOT_URL;
    }

    public String getADMIN_EMAIL() {
	return ADMIN_EMAIL;
    }

    public String getADMIN_EMAIL_PSWRD() {
	return ADMIN_EMAIL_PSWRD;
    }

    public String getSYSTEM_BUILD() {
	return SYSTEM_BUILD;
    }

//@formatter:off
    public String toString() {
	return  "DB_URL:\t\t\t" + UtilVars.PII_START + DB_URL + UtilVars.PII_END +
		"\nROOT_URL:\t\t" + ROOT_URL + 
		"\nADMIN_EMAIL:\t\t" + UtilVars.PII_START + ADMIN_EMAIL + UtilVars.PII_END + 
		"\nADMIN_EMAIL_PSWRD:\t" + UtilVars.PII_START + ADMIN_EMAIL_PSWRD + UtilVars.PII_END + 
		"\nSYSTEM_BUILD:\t\t" + SYSTEM_BUILD;
    }
// @formatter:on
}
