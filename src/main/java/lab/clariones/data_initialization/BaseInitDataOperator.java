package lab.clariones.data_initialization;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseInitDataOperator {
    protected String sql;
    protected List<String> dataList;
    protected boolean cleanAllExistedDataInTable = false;
    Pattern ptnSpliter = Pattern.compile("(?<!(\\\\)),");

    
    public boolean isCleanAllExistedDataInTable() {
        return cleanAllExistedDataInTable;
    }

    public void setCleanAllExistedDataInTable(boolean cleanAllExistedDataInTable) {
        this.cleanAllExistedDataInTable = cleanAllExistedDataInTable;
    }

    public String getSql() {
	return sql;
    }

    public void setSql(String sql) {
	this.sql = sql;
    }

    public List<String> getDataList() {
	return dataList;
    }

    public void setDataList(List<String> dataList) {
	this.dataList = dataList;
    }

    public int execute(Connection dbConn) throws Exception {
	if (cleanAllExistedDataInTable) {
	    String tableName = getTargetTableName();
	    String sql = "delete from " + tableName;
	    Statement stClear = dbConn.createStatement();
	    System.out.println("Execute delete sql: " + sql+", " + getSql());
	    stClear.execute(sql);
	    stClear.close();
	}
	CallableStatement st = null;
	int done = 0;
	try {
	    st = dbConn.prepareCall(getSql());
	    for (String dataStr : dataList) {
		System.out.println("insert data: " + dataStr);
		prepareParameters(st, dataStr);
		done += st.executeUpdate();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (st != null) {
		try {
		    st.close();
		} catch (SQLException e) {
		}
	    }
	}
	return done;
    }

    protected static final Pattern ptnTableName = Pattern.compile("\\s*insert\\s+into\\s+([\\S]+).*");
    private String getTargetTableName() {
	Matcher m = ptnTableName.matcher(getSql());
	if (m.matches()){
	    return m.group(1);
	}
	return null;
    }

    protected String trimValue(Object object) {
	if (object == null) {
	    return "";
	}
	return String.valueOf(object).trim().replaceAll("^[\\s\\p{Z}]+", "").replaceAll("[\\s\\p{Z}]+$", "");
    }

    protected abstract void prepareParameters(CallableStatement st, String dataStr) throws Exception;

    protected String[] splitDataStr(String dataStr) {
	String[] result =  ptnSpliter.split(dataStr);
	for(int i =0;i<result.length;i++){
	    result[i] = result[i].replace("\\,", ",");
	}
	return result;
    }

}
