/*Copyright (c) 2021-2022 innoqa.es All Rights Reserved.
 This software is the confidential and proprietary information of innoqa.es You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with innoqa.es*/
package es.innoqa.apptour.salesdb.dao;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wavemaker.runtime.data.dao.WMGenericDaoImpl;
import com.wavemaker.runtime.data.dao.query.types.wmql.WMQLTypeHelper;

import es.innoqa.apptour.salesdb.Sales;

/**
 * Specifies methods used to obtain and modify Sales related information
 * which is stored in the database.
 */
@Repository("salesdb.SalesDao")
public class SalesDao extends WMGenericDaoImpl<Sales, Integer> {

    @Autowired
    @Qualifier("salesdbTemplate")
    private HibernateTemplate template;

    @Autowired
    @Qualifier("salesdbWMQLTypeHelper")
    private WMQLTypeHelper wmqlTypeHelper;


    @Override
    public HibernateTemplate getTemplate() {
        return this.template;
    }

    @Override
    public WMQLTypeHelper getWMQLTypeHelper() {
        return this.wmqlTypeHelper;
    }

}
