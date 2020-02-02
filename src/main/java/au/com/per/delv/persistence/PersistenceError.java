package au.com.per.delv.persistence;

public enum PersistenceError {
	PARAMETER_NULL(0, "Parameter is null"),
	NOT_FOUND(1, "Object not found"),
	ALREADY_EXIST(2, "Object already exists");
	
	private final int code;
	private final String description; 
	
	private PersistenceError(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
}
