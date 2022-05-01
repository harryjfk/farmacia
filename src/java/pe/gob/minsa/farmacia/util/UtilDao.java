package pe.gob.minsa.farmacia.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UtilDao {

    public static String getStringFromNull(ResultSet rs, String nameColumn) throws SQLException {
        String nValue = rs.getString(nameColumn);
        return rs.wasNull() ? "" : nValue;
    }

    public static String getStringFromNull(ResultSet rs, int indexColumn) throws SQLException {
        String nValue = rs.getString(indexColumn);
        return rs.wasNull() ? "" : nValue;
    }
    
    public static Integer getIntegerFromNull(ResultSet rs, int indexColumn) throws SQLException {
        Integer nValue = rs.getInt(indexColumn);
        return rs.wasNull() ? null : nValue;
    }
    
    public static Integer getIntegerFromNull(ResultSet rs, String nameColumn) throws SQLException {
        Integer nValue = rs.getInt(nameColumn);
        return rs.wasNull() ? null : nValue;
    }
    
    public static Double getDoubleFromNull(ResultSet rs, int indexColumn) throws SQLException {
        Double nValue = rs.getDouble(indexColumn);
        return rs.wasNull() ? null : nValue;
    }
    
    public static Timestamp getTimestampFromNull(ResultSet rs, int indexColumn) throws SQLException {
        Timestamp nValue = rs.getTimestamp(indexColumn);
        return rs.wasNull() ? null: nValue;
    }
    
    public static Timestamp getTimestampFromNull(ResultSet rs, String nameColumn) throws SQLException {
        Timestamp nValue = rs.getTimestamp(nameColumn);
        return rs.wasNull() ? null: nValue;
    }
    
    public static BigDecimal getBigDecimalFromNull(ResultSet rs, String nameColumn) throws SQLException {
        BigDecimal nValue = rs.getBigDecimal(nameColumn);
        return rs.wasNull() ? null : nValue;
    }
}
