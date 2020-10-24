package com.bookmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.bookmanage.dao.BookDao;
import com.bookmanage.dto.BookDto;
import com.bookmanage.dto.UserDto;
import com.bookmanage.util.PageParam;
import com.mysql.cj.xdevapi.JsonArray;
import com.sun.xml.internal.ws.server.ServerRtException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BookService {
    private final static BookDao bookDao=new BookDao();

    /**
     * 通过图书id获取图书信息
     * @param bookId
     * @return
     */
    public BookDto selectBookByBookId(Long bookId){
        String sql="select * from book_content where bid = ?";
        List<Object> paramList=new ArrayList<>();
        paramList.add(bookId);
        List<BookDto> list = bookDao.list(sql, paramList);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据各种条件分页查询图书
     * @param pageParam
     * @param otherParam
     * @return
     */
    public List<BookDto> selectByPage(PageParam pageParam, HashMap<String,Object> otherParam){
        String sql="select b.*,u.name name from book_content b left join sys_user u on b.created=u.user_id where 1=1 ";
        List<Object> paramList=new ArrayList<>();
        if (otherParam!=null){
            if (otherParam.get("status")!=null){
                //根据图书状态
                sql+=" and  b.status= ? ";
                paramList.add(otherParam.get("status"));
            }
            if (otherParam.get("name")!=null){
                //根据上传用户查找
                sql+=" and u.name = ? ";
                paramList.add(otherParam.get("name"));
            }
            if(otherParam.get("bookName")!=null){
                //根据书名模糊查找
                sql+=" and b.title like ? ";
                paramList.add(otherParam.get("bookName"));
            }
            if(otherParam.get("type")!=null){
                //根据类型模糊查找
                sql+=" and b.type like ? ";
                paramList.add(otherParam.get("type"));
            }
            if(otherParam.get("tags")!=null){
                //根据标签模糊查找
                sql+=" and b.tags like ? ";
                paramList.add(otherParam.get("tags"));
            }
            if(otherParam.get("author")!=null){
                //根据作者查找
                sql+=" and b.author = ? ";
                paramList.add(otherParam.get("author"));
            }

            if (otherParam.get("sort")!=null){
                //根据上传用户查找
                sql+=" order by b.gmt_create";
                if (otherParam.get("sort")=="2")
                    sql+=" desc";
            }
        }
        sql+=" limit ? , ?";
        paramList.add(pageParam.getOffset());//获取偏移量
        paramList.add(pageParam.getPageSize());//获取每页条数
        List<BookDto> list = bookDao.list(sql, paramList);
        return list;


    }

    public Integer selectCountByParam(HashMap<String,Object> otherParam){
        String sql="select count(*) from book_content b left join sys_user u on b.created=u.user_id where 1=1";
        List<Object> paramList=new ArrayList<>();
        if (otherParam!=null){

            if (otherParam.get("name")!=null){
                //根据上传用户查找
                sql+=" and u.name = ? ";
                paramList.add(otherParam.get("name"));
            }
            if(otherParam.get("BookName")!=null){
                //根据书名模糊查找
                sql+=" and b.title like ? ";
                paramList.add(otherParam.get("BookName"));
            }
            if(otherParam.get("type")!=null){
                //根据类型模糊查找
                sql+=" and b.type like ? ";
                paramList.add(otherParam.get("type"));
            }
            if(otherParam.get("tags")!=null){
                //根据标签模糊查找
                sql+=" and b.tags like ? ";
                paramList.add(otherParam.get("tags"));
            }
            if(otherParam.get("author")!=null){
                //根据作者查找
                sql+=" and b.author = ? ";
                paramList.add(otherParam.get("author"));
            }
        }
        Integer count = bookDao.count(sql, paramList);
        return count;
    }

    /**
     * 添加图书
     * @param bookDto
     * @param user
     * @return
     */
    public boolean addBook(BookDto bookDto,UserDto user){
        String sql="insert into book_content (`title`,`created`,`desc`,`type`,`is_download`,`download_pay`,`author`,`gtm_create`,`status`) values (?,?,?,?,?,?,?,?,1)";

        List<Object> paramList=new ArrayList<>();
        paramList.add(bookDto.getTitle());
        paramList.add(user.getUserId());
        paramList.add(bookDto.getDesc());
        paramList.add(bookDto.getType());
        paramList.add(bookDto.getIsDownload());
        paramList.add(bookDto.getDownloadPay());
        paramList.add(bookDto.getAuthor());
        paramList.add(new Date());
        int insert = bookDao.insert(sql, paramList);
        if (insert>0){

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 修改图书
     * @param bookDto
     * @return
     */
    public boolean updateById(BookDto bookDto){
        String sql="update book_content set  `desc`=?,is_download=?,download_pay=?,modified=?,gtm_modified=? ";
        if (bookDto.getType()!=null){
            sql+=" ,type=? ";
        }
        sql+=" where bid=?";

        List<Object> paramList=new ArrayList<>();
        paramList.add(bookDto.getDesc());
        paramList.add(bookDto.getIsDownload());
        paramList.add(bookDto.getDownloadPay());
        paramList.add(bookDto.getModified());
        paramList.add(new Date());
        if (bookDto.getType()!=null){
            paramList.add(bookDto.getType());
        }
        paramList.add(bookDto.getBid());
        int insert = bookDao.updateByDto(sql, paramList);
        if (insert>0){
            return true;
        }
        else {
            return false;
        }
    }


    public boolean updateStatus(BookDto bookDto){
        String sql="update book_content set status=? where bid=?";

        List<Object> paramList=new ArrayList<>();
        paramList.add(bookDto.getStatus());
        paramList.add(bookDto.getBid());
        int insert = bookDao.updateByDto(sql, paramList);
        if (insert>0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteById(Long bid){
        String sql="delete from book_content where bid=?";

        List<Object> paramList=new ArrayList<>();
        paramList.add(bid);
        int insert = bookDao.updateByDto(sql, paramList);
        if (insert>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean deleteByIds(JSONArray bids){
        String sql="delete from book_content where bid in (";
        for (Object bid : bids) {
            sql+="?,";
        }
        sql=sql.substring(0,sql.length()-1)+")";

        List<Object> paramList=new ArrayList<>();

        for (Object bid : bids) {
            paramList.add(bid);
        }
        int insert = bookDao.updateByDto(sql, paramList);
        if (insert>0){
            return true;
        }
        else {
            return false;
        }
    }
}
