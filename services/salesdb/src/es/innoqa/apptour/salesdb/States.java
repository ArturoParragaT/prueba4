/*Copyright (c) 2021-2022 innoqa.es All Rights Reserved.
 This software is the confidential and proprietary information of innoqa.es You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with innoqa.es*/
package es.innoqa.apptour.salesdb;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * States generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`STATES`")
public class States implements Serializable {

    private Integer id;
    private String name;
    private String abbrev;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`NAME`", nullable = false, length = 40)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "`ABBREV`", nullable = false, length = 2)
    public String getAbbrev() {
        return this.abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof States)) return false;
        final States states = (States) o;
        return Objects.equals(getId(), states.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
