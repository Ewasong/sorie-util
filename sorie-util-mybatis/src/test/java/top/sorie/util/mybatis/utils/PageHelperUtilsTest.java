package top.sorie.util.mybatis.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import top.sorie.util.mybatis.annotation.CustomOrder;
import top.sorie.util.mybatis.annotation.DefaultOrder;
import top.sorie.util.mybatis.annotation.TableAlias;
import top.sorie.util.mybatis.domain.PageDomain;

import static org.junit.jupiter.api.Assertions.*;

class PageHelperUtilsTest {


    MockedStatic<TableSupport> mockedTableSupport;
    @BeforeEach
    public void before() {
        mockedTableSupport = Mockito.mockStatic(TableSupport.class);
    }
    @AfterEach
    public void after() {
        mockedTableSupport.close();
    }
    @Test
    void testStartPage() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(10);
        pageDomain.setPageSize(20);
        pageDomain.setOrderByColumn("user_name");
        mockedTableSupport.when(TableSupport::buildPageRequest).thenReturn(
                pageDomain
        );
        PageHelperUtils.startPage();
        Page<Object> localPage = PageHelper.getLocalPage();
        String orderBy = localPage.getOrderBy();
        assertEquals("user_name asc", orderBy);
    }

    @Test
    void testStartPageByDefaultOrder() {
        @DefaultOrder(tableName = "user", column = "userName")
        class UserVO {
            private String userName;
        }
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(10);
        pageDomain.setPageSize(20);

        mockedTableSupport.when(TableSupport::buildPageRequest).thenReturn(
                pageDomain
        );
        PageHelperUtils.startPage(UserVO.class);
        Page<Object> localPage = PageHelper.getLocalPage();
        String orderBy = localPage.getOrderBy();
        assertEquals("user.user_name asc", orderBy);
    }

    @Test
    void testStartPageByDefaultOrder2() {
        @DefaultOrder(tableName = "user", column = "user_name")
        class UserVO {
            private String userName;
        }
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(10);
        pageDomain.setPageSize(20);

        mockedTableSupport.when(TableSupport::buildPageRequest).thenReturn(
                pageDomain
        );
        PageHelperUtils.startPage(UserVO.class);
        Page<Object> localPage = PageHelper.getLocalPage();
        String orderBy = localPage.getOrderBy();
        assertEquals("user.user_name asc", orderBy);
    }

    @Test
    void testStartPageByTableAlias() {
        class UserVO {
            @TableAlias("user")
            private String userName;
        }
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(10);
        pageDomain.setPageSize(20);
        pageDomain.setOrderByColumn("userName");

        mockedTableSupport.when(TableSupport::buildPageRequest).thenReturn(
                pageDomain
        );
        PageHelperUtils.startPage(UserVO.class);
        Page<Object> localPage = PageHelper.getLocalPage();
        String orderBy = localPage.getOrderBy();
        assertEquals("user.user_name asc", orderBy);
    }

    @Test
    void testStartPageByCustomOrder() {
        @CustomOrder(tableName = "user", column = "createTime")
        class UserVO extends BaseEntity {
            private String userName;
        }
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(10);
        pageDomain.setPageSize(20);
        pageDomain.setOrderByColumn("createTime");
        pageDomain.setIsAsc("desc");

        mockedTableSupport.when(TableSupport::buildPageRequest).thenReturn(
                pageDomain
        );
        PageHelperUtils.startPage(UserVO.class);
        Page<Object> localPage = PageHelper.getLocalPage();
        String orderBy = localPage.getOrderBy();
        assertEquals("user.create_time desc", orderBy);
    }

    @Test
    void testStartPageByCustomOrder2() {
        @CustomOrder(tableName = "user", column = "create_time")
        class UserVO extends BaseEntity {
            private String userName;
        }
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(10);
        pageDomain.setPageSize(20);
        pageDomain.setOrderByColumn("createTime");
        pageDomain.setIsAsc("desc");

        mockedTableSupport.when(TableSupport::buildPageRequest).thenReturn(
                pageDomain
        );
        PageHelperUtils.startPage(UserVO.class);
        Page<Object> localPage = PageHelper.getLocalPage();
        String orderBy = localPage.getOrderBy();
        assertEquals("user.create_time desc", orderBy);
    }
}