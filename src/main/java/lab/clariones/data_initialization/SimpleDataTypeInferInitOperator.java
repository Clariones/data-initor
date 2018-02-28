package lab.clariones.data_initialization;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDataTypeInferInitOperator extends BaseInitDataOperator {
	protected void prepareParameters(CallableStatement st, String dataStr) throws Exception {
		String[] params = splitDataStr(dataStr);
		for (int i = 0; i < params.length; i++) {
			String value = trimValue(params[i]);
			setWithInferDataType(st, i + 1, value);
		}
	}

	private void setWithInferDataType(CallableStatement st, int pos, String value)
			throws NumberFormatException, SQLException, ParseException {
		Matcher m;
		if ("NULL".equals(value)){
			st.setObject(pos, null);
			return;
		}
		
		m = ptnDouble.matcher(value);
		if (m.matches()) {
			st.setDouble(pos, Double.parseDouble(value));
			return;
		}

		m = ptnDateTime.matcher(value);
		if (m.matches()) {
			st.setTimestamp(pos, new Timestamp(fmtDateTime.parse(value).getTime()));
			return;
		}

		m = ptnDateTime2.matcher(value);
		if (m.matches()) {
			st.setTimestamp(pos, new Timestamp(fmtDateTime2.parse(value).getTime()));
			return;
		}

		m = ptnDate.matcher(value);
		if (m.matches()) {
			st.setDate(pos, new java.sql.Date(fmtDate.parse(value).getTime()));
			return;
		}

		m = ptnChinaMobile.matcher(value);
		if (m.matches()) {
			st.setString(pos, value);
			return;
		}

		m = ptnLong.matcher(value);
		if (m.matches()) {
			st.setLong(pos, Long.parseLong(value));
			return;
		}

		m = ptnInteger.matcher(value);
		if (m.matches()) {
			st.setInt(pos, Integer.parseInt(value));
			return;
		}

		st.setString(pos, value);

	}

	Pattern ptnDouble = Pattern.compile("\\d+\\.\\d+");
	Pattern ptnDateTime = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
	Pattern ptnDateTime2 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}");
	Pattern ptnDate = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
	Pattern ptnChinaMobile = Pattern.compile("1[34578][0-9]{9}");
	Pattern ptnLong = Pattern.compile("\\d+[lL]");
	Pattern ptnInteger = Pattern.compile("\\d+");

	SimpleDateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat fmtDateTime2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
}
