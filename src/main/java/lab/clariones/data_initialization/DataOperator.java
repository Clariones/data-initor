package lab.clariones.data_initialization;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public class DataOperator {
    protected List<BaseInitDataOperator> initors;
    protected DataSource dataSource;
    
    public DataSource getDataSource() {
        return dataSource;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<BaseInitDataOperator> getInitors() {
        return initors;
    }


    public void setInitors(List<BaseInitDataOperator> initors) {
        this.initors = initors;
    }


    public void startWork() throws Exception {
	Connection dbConn = dataSource.getConnection();
	for(BaseInitDataOperator initor : initors){
	    initor.execute(dbConn);
	}
    }

}
