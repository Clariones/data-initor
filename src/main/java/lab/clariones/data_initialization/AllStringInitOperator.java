package lab.clariones.data_initialization;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class AllStringInitOperator extends BaseInitDataOperator {
	protected void prepareParameters(CallableStatement st, String dataStr) throws SQLException {
		String[] params = splitDataStr(dataStr);
		for (int i = 0; i < params.length; i++) {

			String value = trimValue(params[i]);
			if ("NULL".equals(value)) {
				st.setObject(i + 1, null);
				continue;
			}
			st.setString(i + 1, value);
		}
	}
}
