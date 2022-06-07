package studentsProj.dao;

import studentsProj.domain.StudentOrder;
import studentsProj.exception.DaoException;

public interface StudentOrderDao {
    Long saveStudentOrder(StudentOrder so) throws DaoException;
}
