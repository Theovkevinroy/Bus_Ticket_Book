package Entities;

public class UserType {
    private int sl;
    private String typeCode;
    private String typeName;
    private String Status;

    public UserType(int sl, String typeCode, String typeName, String status) {
        this.sl = sl;
        this.typeCode = typeCode;
        this.typeName = typeName;
        Status = status;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "sl=" + sl +
                ", typeCode='" + typeCode + '\'' +
                ", typeName='" + typeName + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
