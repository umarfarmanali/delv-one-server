package au.com.per.delv;

public enum EnumError {
	ALL_OK(0, "Success"),
	PARAMETER_NULL(1, "Parameter is null"),
	NOT_FOUND(2, "Object not found"),
	ALREADY_EXIST(3, "Object already exists"),
	EXCEPTION(4, "Exception: <token>"),
	MISSING_RELATION(5, "Missing relationship: <token>");
	
	
	private final int code;
	private final String description; 
	
	private EnumError(int code, String description) {
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
	  
	  public String toString(String message) {
		    return code + ": " + description.replace("<token>", message);
	}
}
