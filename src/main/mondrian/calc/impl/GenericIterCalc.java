/*
// $Id$
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2008-2008 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.calc.impl;

import mondrian.olap.*;
import mondrian.olap.type.SetType;
import mondrian.calc.*;

import java.util.*;

/**
 * Adapter which computes a set expression and converts it to any list or
 * iterable type.
 *
 * @author jhyde
 * @version $Id$
 * @since Nov 7, 2008
 */
public abstract class GenericIterCalc
    extends AbstractCalc
    implements ListCalc, MemberListCalc, TupleListCalc,
    IterCalc, TupleIterCalc, MemberIterCalc
{
    protected GenericIterCalc(Exp exp) {
        super(exp);
    }

    public SetType getType() {
        return (SetType) type;
    }

    public List evaluateList(Evaluator evaluator) {
        Object o = evaluate(evaluator);
        if (o instanceof List) {
            return (List) o;
        } else {
            // Iterable
            final Iterable iter = (Iterable) o;
            Iterator it = iter.iterator();
            List<Object> list = new ArrayList<Object>();
            while (it.hasNext()) {
                list.add(it.next());
            }
            return list;
        }
    }
    @SuppressWarnings({"unchecked"})
    public final List<Member> evaluateMemberList(Evaluator evaluator) {
        return (List<Member>) evaluateList(evaluator);
    }

    @SuppressWarnings({"unchecked"})
    public final List<Member[]> evaluateTupleList(Evaluator evaluator) {
        return (List<Member[]>) evaluateList(evaluator);
    }

    public Iterable evaluateIterable(Evaluator evaluator) {
        Object o = evaluate(evaluator);
        if (o instanceof Iterable) {
            return (Iterable) o;
        } else {
            final List list = (List) o;
            // for java4 must convert List into an Iterable
            return new Iterable() {
                public Iterator iterator() {
                    return list.iterator();
                }
            };
        }
    }

    @SuppressWarnings({"unchecked"})
    public Iterable<Member> evaluateMemberIterable(Evaluator evaluator) {
        return (Iterable<Member>) evaluateIterable(evaluator);
    }

    @SuppressWarnings({"unchecked"})
    public Iterable<Member[]> evaluateTupleIterable(Evaluator evaluator) {
        return (Iterable<Member[]>) evaluateIterable(evaluator);
    }
}

// End GenericIterCalc.java