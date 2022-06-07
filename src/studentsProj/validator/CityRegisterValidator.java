package studentsProj.validator;

import studentsProj.domain.Person;
import studentsProj.domain.register.AnswerCityRegister;
import studentsProj.domain.Child;
import studentsProj.domain.register.AnswerCityRegisterItem;
import studentsProj.domain.register.CityRegisterResponse;
import studentsProj.domain.StudentOrder;
import studentsProj.exception.CityRegisterException;
import studentsProj.exception.TransportException;
import studentsProj.validator.register.CityRegisterChecker;
import studentsProj.validator.register.FakeCityRegisterChecker;

public class CityRegisterValidator
{
    public static final String IN_CODE = "NO_GRN";
    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder so)  {
        AnswerCityRegister ans = new AnswerCityRegister();

        ans.addItem(checkPerson(so.getHusband()));
        ans.addItem(checkPerson(so.getWife()));

        for (Child child : so.getChildren()){
            ans.addItem(checkPerson(child));
        }

        return ans;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {
        AnswerCityRegisterItem.CityStatus status = null;
        AnswerCityRegisterItem.CityError error = null;

        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isExisting() ?
                    AnswerCityRegisterItem.CityStatus.YES :
                    AnswerCityRegisterItem.CityStatus.NO;

        } catch (CityRegisterException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        } catch (TransportException ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }

        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);

        return ans;
    }

}

//            for (int i = 0; i < children.size(); i++){
//                CityRegisterCheckerResponse cAns = personChecker.checkPerson(children.get(i));
//            }
//
//            for (Iterator<Child> it = children.iterator(); it.hasNext(); ) {
//                Child child = it.next();
//                CityRegisterCheckerResponse cAns = personChecker.checkPerson(child);
//            }