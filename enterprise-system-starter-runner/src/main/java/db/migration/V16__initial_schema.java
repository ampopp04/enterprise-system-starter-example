package db.migration;

import com.starter.entities.Department;
import com.starter.entities.Employee;
import com.system.db.entity.Entity;
import com.system.db.migration.table.TableCreationMigration;
import com.system.db.repository.base.named.NamedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.system.util.collection.CollectionUtils.asList;

/**
 * The <class>V16__initial_schema</class> defines the initial schema for
 * the basic enterprise starter system.
 *
 * @author Andrew
 */
public class V16__initial_schema extends TableCreationMigration {

    ///////////////////////////////////////////////////////////////////////
    ////////                                                     Properties                                                       //////////
    //////////////////////////////////////////////////////////////////////

    @Autowired
    private NamedEntityRepository<Department> departmentRepository;

    @Autowired
    private NamedEntityRepository<Employee> employeeRepository;

    ///////////////////////////////////////////////////////////////////////
    ////////                                                 Table Creation                                                  //////////
    //////////////////////////////////////////////////////////////////////

    protected List<Class<? extends Entity>> getEntityClasses() {
        return asList(
                Department.class, Employee.class
        );
    }

    ///////////////////////////////////////////////////////////////////////
    ////////                                                  Data Insertion                                                   //////////
    //////////////////////////////////////////////////////////////////////

    @Override
    protected void insertData() {
        //Create basic mock data
        getDepartmentRepository().saveAll(getDepartmentList());
        getEmployeeRepository().saveAll(getEmployeeList());
    }

    private List<Department> getDepartmentList() {
        return asList(
                Department.newInstance("Production", "Creates the products for the warehouse."),
                Department.newInstance("Purchasing", "Buys materials for production."),
                Department.newInstance("Sales", "Sells the products from the warehouse."),
                Department.newInstance("Marketing", "Advertises the products we sell."),
                Department.newInstance("Engineering", "Develops the products that are created."),
                Department.newInstance("Accounting", "Accounts for all of our profits and losses.")
        );
    }

    private List<Employee> getEmployeeList() {
        Employee basicEmployee = new Employee();
        basicEmployee.setFirstName("John");
        basicEmployee.setLastName("Doe");
        basicEmployee.setBusinessPhone("123.321.234");
        basicEmployee.setCity("Minneapolis");
        basicEmployee.setState("MN");
        basicEmployee.setCountry("USA");
        basicEmployee.setCompany("Quality Consulting");
        basicEmployee.setJobTitle("The Boss");
        basicEmployee.setEnabled(true);
        basicEmployee.setZipCode("55129");
        basicEmployee.setDepartment(getDepartmentRepository().findByName("Engineering"));
        return asList(basicEmployee);
    }

    ///////////////////////////////////////////////////////////////////////
    ////////                                             Basic   Getter/Setters                                          //////////
    //////////////////////////////////////////////////////////////////////

    public NamedEntityRepository<Department> getDepartmentRepository() {
        return departmentRepository;
    }

    public void setDepartmentRepository(NamedEntityRepository<Department> departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public NamedEntityRepository<Employee> getEmployeeRepository() {
        return employeeRepository;
    }

    public void setEmployeeRepository(NamedEntityRepository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}