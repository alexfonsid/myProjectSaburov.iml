package studentsProj.dao;

import studentsProj.domain.CountryArea;
import studentsProj.domain.PassportOffice;
import studentsProj.domain.RegisterOffice;
import studentsProj.domain.Street;
import studentsProj.exception.DaoException;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreets(String pattern) throws DaoException;
    List<PassportOffice> findPassportOffices(String areaId) throws DaoException;
    List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException;
    List<CountryArea> findAreas(String areaId) throws DaoException;
}
