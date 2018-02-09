package lab.clariones.data_initialization;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class AllStringInitOperator extends BaseInitDataOperator {
	protected void prepareParameters(CallableStatement st, String dataStr) throws SQLException {
	    String[] params = splitDataStr(dataStr);
		for(int i=0;i<params.length;i++){
			st.setString(i+1, trimValue(params[i]));
		}
	}
}
