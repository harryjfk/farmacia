/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.helpers;

import java.util.Arrays;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author armando
 */
public class JpaCriteriaHelper {
   
    public static Predicate[] agregarPredicado(Predicate[] predicates, Predicate predicate) {
        predicates = Arrays.copyOf(predicates, predicates.length + 1);
        predicates[predicates.length - 1] = predicate;
        return predicates;
    }
}
