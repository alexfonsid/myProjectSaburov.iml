package studentsProj.validator.register;

import studentsProj.domain.register.CityRegisterResponse;
import studentsProj.domain.Person;
import studentsProj.exception.CityRegisterException;
import studentsProj.exception.TransportException;

public class RealCityRegisterChecker implements CityRegisterChecker {
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        return null;
    }
}
