package org.outman.dragonfly.admin.entity;

import lombok.*;

/**
 * @ClassName Result
 * @Description TODO
 * @Author OutMan
 * @create 2021-05-06 14:31
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code = 0;

    private T data;

    private String msg;

    private long count;
}
