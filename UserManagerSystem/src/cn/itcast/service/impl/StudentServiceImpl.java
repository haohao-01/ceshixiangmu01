package cn.itcast.service.impl;

import cn.itcast.dao.StudentDao;
import cn.itcast.dao.impl.StudentDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Student;
import cn.itcast.service.StudentService;

import java.util.List;
import java.util.Map;

public class StudentServiceImpl implements StudentService {
    private StudentDao dao = new StudentDaoImpl();

    @Override
    public List<Student> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

   // @Override
    //public Student login(Student student) {
       // return dao.findUserByUsernameAndPassword(student.getUsername(), student.getPassword());
    //}

    @Override
    public void addUser(Student student) {
        dao.add(student);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public Student findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(Student student) {
        dao.update(student);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        if(ids != null && ids.length > 0){
            //1.遍历数组
            for (String id : ids) {
                //2.调用dao删除
                dao.delete(Integer.parseInt(id));
            }
        }

    }

    @Override
    public PageBean<Student> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        if(currentPage <=0) {
            currentPage = 1;
        }
        //1.创建空的PageBean对象
        PageBean<Student> pb = new PageBean<Student>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3.调用dao查询总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //4.调用dao查询List集合
        //计算开始的记录索引
        int start = (currentPage - 1) * rows;
        List<Student> list = dao.findByPage(start,rows,condition);
        pb.setList(list);

        //5.计算总页码
        int totalPage = (totalCount % rows)  == 0 ? totalCount/rows : (totalCount/rows) + 1;
        pb.setTotalPage(totalPage);


        return pb;
    }
}
