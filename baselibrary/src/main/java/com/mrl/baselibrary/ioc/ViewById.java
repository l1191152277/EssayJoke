package com.mrl.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L on 2017/5/7
 * Description: View注解的Annotation
 */

//@Target(ElementType.FIELD)代表Annotation的位置  DIELD属性上  TYPE类上   CONSTRUCTOR构造函数上
@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.CLASS) 什么时候生效  CLASS 编译时生效  RUNTIME 运行时生效  SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    // --> @ViewById(R.id.xxxx)
    int value();
}
