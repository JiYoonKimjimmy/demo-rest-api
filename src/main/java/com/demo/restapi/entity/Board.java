package com.demo.restapi.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board implements Serializable {
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "제목", required = true)
    @Column(length = 20)
    private String title;

    @ApiModelProperty(value = "내용", required = true)
    @Column(length = 200)
    private String context;

    @ApiModelProperty(value = "작성자", required = true)
    private String author;

    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;
}
