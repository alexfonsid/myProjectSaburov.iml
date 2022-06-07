package studentsProj.dao;

import studentsProj.config.Config;
import studentsProj.domain.Address;
import studentsProj.domain.Adult;
import studentsProj.domain.StudentOrder;
import studentsProj.domain.StudentOrderStatus;
import studentsProj.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao {
    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    " student_order_status, student_order_date, h_sur_name, " +
                    " h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, " +
                    " h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
                    " h_street_code, h_building, h_extension, h_apartment, w_sur_name, " +
                    " w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, " +
                    " w_passport_number, w_passport_date, w_passport_office_id, w_post_index, " +
                    " w_street_code, w_building, w_extension, w_apartment, " +
                    "certificate_id, register_office_id, marriage_date)" +
                    " VALUES (?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, " +
                    "?, ?);";

    //    TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        long result = -1L;


        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {
            // Header
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            // Husband
            Adult husband = so.getHusband();
            stmt.setString(3, husband.getSurName());
            stmt.setString(4, husband.getGivenName());
            stmt.setString(5, husband.getPatronymic());
            stmt.setDate(6, java.sql.Date.valueOf(husband.getDate0fBirth()));
            stmt.setString(7, husband.getPassportSeria()); // h_passport_seria,
            stmt.setString(8, husband.getPassportNumber()); // h_passport_number,
            stmt.setDate(9, java.sql.Date.valueOf(husband.getIssueDate()));//                    h_passport_date,
            stmt.setLong(10, husband.getIssueDepartment().getOfficeId());//                    h_passport_office_id,
            Address hAddress = so.getHusband().getAddress();
            stmt.setString(11, hAddress.getPostCode());//            "h_post_index, " +
            stmt.setLong(12, hAddress.getStreet().getStreetCode());//                    "h_street_code,
            stmt.setString(13, hAddress.getBuilding());//                    "h_building, " +
            stmt.setString(14, hAddress.getExtension());//                    "h_extension, " +
            stmt.setString(15, hAddress.getApartment());
            // Wife
            Adult wife = so.getWife();
            stmt.setString(16, wife.getSurName());
            stmt.setString(17, wife.getGivenName());
            stmt.setString(18, wife.getPatronymic());
            stmt.setDate(19, java.sql.Date.valueOf(wife.getDate0fBirth()));
            stmt.setString(20, wife.getPassportSeria()); // w_passport_seria,
            stmt.setString(21, wife.getPassportNumber()); // w_passport_number,
            stmt.setDate(22, java.sql.Date.valueOf(wife.getIssueDate()));// w_passport_date,
            stmt.setLong(23, wife.getIssueDepartment().getOfficeId());// w_passport_office_id,
            Address wAddress = so.getWife().getAddress();
            stmt.setString(24, wAddress.getPostCode());// w_post_index, " +
            stmt.setLong(25, wAddress.getStreet().getStreetCode());// w_street_code,
            stmt.setString(26, wAddress.getBuilding());//  w_building, " +
            stmt.setString(27, wAddress.getExtension());// w_extension, " +
            stmt.setString(28, wAddress.getApartment());// w_apartment, " +
            // Marriage
            stmt.setString(29, so.getMarriageCertificateId());// certificate_id, " +
            stmt.setLong(30, so.getMarriageOffice().getOfficeId());//                    "register_office_id, " +
            stmt.setDate(31, java.sql.Date.valueOf(so.getMarriageDate()));//                    "marriage_date

            stmt.executeUpdate();

            ResultSet genKeysResultSet = stmt.getGeneratedKeys();
            if (genKeysResultSet.next()) {
                result = genKeysResultSet.getLong(1);
            }
            genKeysResultSet.close();

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return result;
    }
}
