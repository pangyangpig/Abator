package com.github.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.github.mybatis.domain.BaseDomain;

/**
 * DAO
 * @author xiebiao
 */
public interface IDao {

    <T> Integer insert(T t) throws DataAccessException;

    Integer delete(String keyId) throws DataAccessException;

    <T> Integer update(T t) throws DataAccessException;

    <T> T find(String keyId) throws DataAccessException;

    <E> List<E> listForObject(E t) throws DataAccessException;

    <E> Integer countForObject(E t) throws DataAccessException;

    <E extends BaseDomain> List<E> insertBatch(final List<E> t);

    <E extends BaseDomain> List<E> updateBatch(final List<E> t);

    <E extends BaseDomain> List<E> updateBatch(final List<E> t, final String updateSqlId);

}
