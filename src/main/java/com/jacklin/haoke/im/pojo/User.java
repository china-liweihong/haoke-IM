package com.jacklin.haoke.im.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 上午10:08
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private Integer id;

    private String username;
}
