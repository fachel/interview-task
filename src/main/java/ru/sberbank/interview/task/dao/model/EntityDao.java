package ru.sberbank.interview.task.dao.model;

import lombok.*;
import ru.sberbank.interview.task.controller.dto.support.EntityDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ENTITY")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EntityDao implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    @Column(name = "CODE")
    private Integer code;
    @Column(name = "SYSNAME")
    private String sysname;
    @Column(name = "WATCHED_DTTM")
    private Date watchedDttm;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DATA")
    private String data;

    public EntityDto toDto(){
        EntityDto entityDto = new EntityDto();
        entityDto.setId(this.id);
        entityDto.setCode(this.code);
        entityDto.setSysname(this.sysname);
        entityDto.setWatchedDttm(this.watchedDttm);
        entityDto.setDescription(this.description);
        entityDto.setData(this.data);
        return entityDto;
    }

}
