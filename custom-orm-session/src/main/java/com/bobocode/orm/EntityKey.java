package com.bobocode.orm;

/**
 * @author Rostyslav Kholodnytskyi
 * @date 13/12/2021
 * @project bobocode-course
 */
public record EntityKey<T>(Class<T> type, Object id) {

}
