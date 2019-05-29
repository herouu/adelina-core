package top.alertcode.adelina.framework.utils;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.collection.*;
import org.apache.commons.collections4.functors.TruePredicate;
import org.apache.commons.collections4.iterators.CollatingIterator;
import org.apache.commons.collections4.iterators.PermutationIterator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>CollectionUtils class.</p>
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class CollectionUtils {
    /**
     * Constant <code>EMPTY_COLLECTION</code>
     */
    public static final Collection EMPTY_COLLECTION =
            UnmodifiableCollection.unmodifiableCollection(new ArrayList<Object>());

    private CollectionUtils() {
    }

    /**
     * <p>emptyCollection.</p>
     *
     * @param <T> a T object.
     * @return a {@link java.util.Collection} object.
     */
    public static <T> Collection<T> emptyCollection() {
        return EMPTY_COLLECTION;
    }

    /**
     * <p>emptyIfNull.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param <T> a T object.
     * @return a {@link java.util.Collection} object.
     */
    public static <T> Collection<T> emptyIfNull(final Collection<T> collection) {
        return collection == null ? EMPTY_COLLECTION : collection;
    }

    /**
     * <p>union.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> union(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        final SetOperationCardinalityHelper<O> helper = new SetOperationCardinalityHelper<O>(a, b);
        for (final O obj : helper) {
            helper.setCardinality(obj, helper.max(obj));
        }
        return helper.list();
    }

    /**
     * <p>intersection.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> intersection(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        final SetOperationCardinalityHelper<O> helper = new SetOperationCardinalityHelper<O>(a, b);
        for (final O obj : helper) {
            helper.setCardinality(obj, helper.min(obj));
        }
        return helper.list();
    }

    /**
     * <p>disjunction.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> disjunction(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        final SetOperationCardinalityHelper<O> helper = new SetOperationCardinalityHelper<O>(a, b);
        for (final O obj : helper) {
            helper.setCardinality(obj, helper.max(obj) - helper.min(obj));
        }
        return helper.list();
    }

    /**
     * <p>subtract.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> subtract(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        final Predicate<O> p = TruePredicate.truePredicate();
        return subtract(a, b, p);
    }

    /**
     * <p>subtract.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param p a {@link org.apache.commons.collections4.Predicate} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> subtract(final Iterable<? extends O> a,
                                             final Iterable<? extends O> b,
                                             final Predicate<O> p) {
        final ArrayList<O> list = new ArrayList<O>();
        final HashBag<O> bag = new HashBag<O>();
        for (final O element : b) {
            if (p.evaluate(element)) {
                bag.add(element);
            }
        }
        for (final O element : a) {
            if (!bag.remove(element, 1)) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * <p>containsAll.</p>
     *
     * @param coll1 a {@link java.util.Collection} object.
     * @param coll2 a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean containsAll(final Collection<?> coll1, final Collection<?> coll2) {
        if (coll2.isEmpty()) {
            return true;
        } else {
            final Iterator<?> it = coll1.iterator();
            final Set<Object> elementsAlreadySeen = new HashSet<Object>();
            for (final Object nextElement : coll2) {
                if (elementsAlreadySeen.contains(nextElement)) {
                    continue;
                }

                boolean foundCurrentElement = false;
                while (it.hasNext()) {
                    final Object p = it.next();
                    elementsAlreadySeen.add(p);
                    if (nextElement == null ? p == null : nextElement.equals(p)) {
                        foundCurrentElement = true;
                        break;
                    }
                }

                if (foundCurrentElement) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * <p>containsAny.</p>
     *
     * @param coll1 a {@link java.util.Collection} object.
     * @param coll2 a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean containsAny(final Collection<?> coll1, final Collection<?> coll2) {
        if (coll1.size() < coll2.size()) {
            for (final Object aColl1 : coll1) {
                if (coll2.contains(aColl1)) {
                    return true;
                }
            }
        } else {
            for (final Object aColl2 : coll2) {
                if (coll1.contains(aColl2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>getCardinalityMap.</p>
     *
     * @param coll a {@link java.lang.Iterable} object.
     * @param <O> a O object.
     * @return a {@link java.util.Map} object.
     */
    public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> coll) {
        final Map<O, Integer> count = new HashMap<O, Integer>();
        for (final O obj : coll) {
            final Integer c = count.get(obj);
            if (c == null) {
                count.put(obj, Integer.valueOf(1));
            } else {
                count.put(obj, Integer.valueOf(c.intValue() + 1));
            }
        }
        return count;
    }

    /**
     * <p>isSubCollection.</p>
     *
     * @param a a {@link java.util.Collection} object.
     * @param b a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean isSubCollection(final Collection<?> a, final Collection<?> b) {
        final CardinalityHelper<Object> helper = new CardinalityHelper<Object>(a, b);
        for (final Object obj : a) {
            if (helper.freqA(obj) > helper.freqB(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>isProperSubCollection.</p>
     *
     * @param a a {@link java.util.Collection} object.
     * @param b a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean isProperSubCollection(final Collection<?> a, final Collection<?> b) {
        return a.size() < b.size() && CollectionUtils.isSubCollection(a, b);
    }

    /**
     * <p>isEqualCollection.</p>
     *
     * @param a a {@link java.util.Collection} object.
     * @param b a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b) {
        if (a.size() != b.size()) {
            return false;
        }
        final CardinalityHelper<Object> helper = new CardinalityHelper<Object>(a, b);
        if (helper.cardinalityA.size() != helper.cardinalityB.size()) {
            return false;
        }
        for (final Object obj : helper.cardinalityA.keySet()) {
            if (helper.freqA(obj) != helper.freqB(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>isEqualCollection.</p>
     *
     * @param a a {@link java.util.Collection} object.
     * @param b a {@link java.util.Collection} object.
     * @param equator a {@link org.apache.commons.collections4.Equator} object.
     * @return a boolean.
     */
    public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b, final Equator<?> equator) {
        if (equator == null) {
            throw new IllegalArgumentException("equator may not be null");
        }

        if (a.size() != b.size()) {
            return false;
        }

        final Transformer transformer = new Transformer() {
            public EquatorWrapper<?> transform(final Object input) {
                return new EquatorWrapper(equator, input);
            }
        };

        return isEqualCollection(collect(a, transformer), collect(b, transformer));
    }

    /**
     * <p>cardinality.</p>
     *
     * @param obj a O object.
     * @param coll a {@link java.lang.Iterable} object.
     * @param <O> a O object.
     * @return a int.
     */
    public static <O> int cardinality(final O obj, final Iterable<? super O> coll) {
        if (coll instanceof Set<?>) {
            return ((Set<? super O>) coll).contains(obj) ? 1 : 0;
        }
        if (coll instanceof Bag<?>) {
            return ((Bag<? super O>) coll).getCount(obj);
        }
        int count = 0;
        if (obj == null) {
            for (final Object element : coll) {
                if (element == null) {
                    count++;
                }
            }
        } else {
            for (final Object element : coll) {
                if (obj.equals(element)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * <p>find.</p>
     *
     * @param collection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T find(final Iterable<T> collection, final Predicate<? super T> predicate) {
        if (collection != null && predicate != null) {
            for (final T item : collection) {
                if (predicate.evaluate(item)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * <p>forAllDo.</p>
     *
     * @param collection a {@link java.lang.Iterable} object.
     * @param closure a C object.
     * @param <T> a T object.
     * @return a C object.
     * @param <C> a C object.
     */
    public static <T, C extends Closure<? super T>> C forAllDo(final Iterable<T> collection, final C closure) {
        if (collection != null && closure != null) {
            for (final T element : collection) {
                closure.execute(element);
            }
        }
        return closure;
    }

    /**
     * <p>forAllDo.</p>
     *
     * @param iterator a {@link java.util.Iterator} object.
     * @param closure a C object.
     * @param <T> a T object.
     * @return a C object.
     * @param <C> a C object.
     */
    public static <T, C extends Closure<? super T>> C forAllDo(final Iterator<T> iterator, final C closure) {
        if (iterator != null && closure != null) {
            while (iterator.hasNext()) {
                closure.execute(iterator.next());
            }
        }
        return closure;
    }

    /**
     * <p>forAllButLastDo.</p>
     *
     * @param collection a {@link java.lang.Iterable} object.
     * @param closure a C object.
     * @param <T> a T object.
     * @return a T object.
     * @param <C> a C object.
     */
    public static <T, C extends Closure<? super T>> T forAllButLastDo(final Iterable<T> collection,
                                                                      final C closure) {
        return collection != null && closure != null ? forAllButLastDo(collection.iterator(), closure) : null;
    }

    /**
     * <p>forAllButLastDo.</p>
     *
     * @param iterator a {@link java.util.Iterator} object.
     * @param closure a C object.
     * @param <T> a T object.
     * @return a T object.
     * @param <C> a C object.
     */
    public static <T, C extends Closure<? super T>> T forAllButLastDo(final Iterator<T> iterator, final C closure) {
        if (iterator != null && closure != null) {
            while (iterator.hasNext()) {
                final T element = iterator.next();
                if (iterator.hasNext()) {
                    closure.execute(element);
                } else {
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * <p>filter.</p>
     *
     * @param collection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <T> a T object.
     * @return a boolean.
     */
    public static <T> boolean filter(final Iterable<T> collection, final Predicate<? super T> predicate) {
        boolean result = false;
        if (collection != null && predicate != null) {
            for (final Iterator<T> it = collection.iterator(); it.hasNext(); ) {
                if (!predicate.evaluate(it.next())) {
                    it.remove();
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * <p>filterInverse.</p>
     *
     * @param collection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <T> a T object.
     * @return a boolean.
     */
    public static <T> boolean filterInverse(final Iterable<T> collection, final Predicate<? super T> predicate) {
        return filter(collection, predicate == null ? null : PredicateUtils.notPredicate(predicate));
    }

    /**
     * <p>transform.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param transformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <C> a C object.
     */
    public static <C> void transform(final Collection<C> collection,
                                     final Transformer<? super C, ? extends C> transformer) {

        if (collection != null && transformer != null) {
            if (collection instanceof List<?>) {
                final List<C> list = (List<C>) collection;
                for (final ListIterator<C> it = list.listIterator(); it.hasNext(); ) {
                    it.set(transformer.transform(it.next()));
                }
            } else {
                final Collection<C> resultCollection = collect(collection, transformer);
                collection.clear();
                collection.addAll(resultCollection);
            }
        }
    }

    /**
     * <p>countMatches.</p>
     *
     * @param input a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <C> a C object.
     * @return a int.
     */
    public static <C> int countMatches(final Iterable<C> input, final Predicate<? super C> predicate) {
        int count = 0;
        if (input != null && predicate != null) {
            for (final C o : input) {
                if (predicate.evaluate(o)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * <p>exists.</p>
     *
     * @param input a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <C> a C object.
     * @return a boolean.
     */
    public static <C> boolean exists(final Iterable<C> input, final Predicate<? super C> predicate) {
        if (input != null && predicate != null) {
            for (final C o : input) {
                if (predicate.evaluate(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>matchesAll.</p>
     *
     * @param input a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <C> a C object.
     * @return a boolean.
     */
    public static <C> boolean matchesAll(final Iterable<C> input, final Predicate<? super C> predicate) {
        if (predicate == null) {
            return false;
        }

        if (input != null) {
            for (final C o : input) {
                if (!predicate.evaluate(o)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <p>select.</p>
     *
     * @param inputCollection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> select(final Iterable<? extends O> inputCollection,
                                           final Predicate<? super O> predicate) {
        final Collection<O> answer = inputCollection instanceof Collection<?> ?
                new ArrayList<O>(((Collection<?>) inputCollection).size()) : new ArrayList<O>();
        return select(inputCollection, predicate, answer);
    }

    /**
     * <p>select.</p>
     *
     * @param inputCollection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param outputCollection a R object.
     * @param <O> a O object.
     * @return a R object.
     * @param <R> a R object.
     */
    public static <O, R extends Collection<? super O>> R select(final Iterable<? extends O> inputCollection,
                                                                final Predicate<? super O> predicate, final R outputCollection) {

        if (inputCollection != null && predicate != null) {
            for (final O item : inputCollection) {
                if (predicate.evaluate(item)) {
                    outputCollection.add(item);
                }
            }
        }
        return outputCollection;
    }

    /**
     * <p>selectRejected.</p>
     *
     * @param inputCollection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <O> Collection<O> selectRejected(final Iterable<? extends O> inputCollection,
                                                   final Predicate<? super O> predicate) {
        final Collection<O> answer = inputCollection instanceof Collection<?> ?
                new ArrayList<O>(((Collection<?>) inputCollection).size()) : new ArrayList<O>();
        return selectRejected(inputCollection, predicate, answer);
    }

    /**
     * <p>selectRejected.</p>
     *
     * @param inputCollection a {@link java.lang.Iterable} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param outputCollection a R object.
     * @param <O> a O object.
     * @return a R object.
     * @param <R> a R object.
     */
    public static <O, R extends Collection<? super O>> R selectRejected(final Iterable<? extends O> inputCollection,
                                                                        final Predicate<? super O> predicate, final R outputCollection) {

        if (inputCollection != null && predicate != null) {
            for (final O item : inputCollection) {
                if (!predicate.evaluate(item)) {
                    outputCollection.add(item);
                }
            }
        }
        return outputCollection;
    }

    /**
     * <p>collect.</p>
     *
     * @param inputCollection a {@link java.lang.Iterable} object.
     * @param transformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <I> a I object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <I, O> Collection<O> collect(final Iterable<I> inputCollection,
                                               final Transformer<? super I, ? extends O> transformer) {
        final Collection<O> answer = inputCollection instanceof Collection<?> ?
                new ArrayList<O>(((Collection<?>) inputCollection).size()) : new ArrayList<O>();
        return collect(inputCollection, transformer, answer);
    }

    /**
     * <p>collect.</p>
     *
     * @param inputIterator a {@link java.util.Iterator} object.
     * @param transformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <I> a I object.
     * @param <O> a O object.
     * @return a {@link java.util.Collection} object.
     */
    public static <I, O> Collection<O> collect(final Iterator<I> inputIterator,
                                               final Transformer<? super I, ? extends O> transformer) {
        return collect(inputIterator, transformer, new ArrayList<O>());
    }

    /**
     * <p>collect.</p>
     *
     * @param inputCollection a {@link java.lang.Iterable} object.
     * @param transformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param outputCollection a R object.
     * @param <I> a I object.
     * @param <O> a O object.
     * @return a R object.
     * @param <R> a R object.
     */
    public static <I, O, R extends Collection<? super O>> R collect(final Iterable<? extends I> inputCollection,
                                                                    final Transformer<? super I, ? extends O> transformer, final R outputCollection) {
        if (inputCollection != null) {
            return collect(inputCollection.iterator(), transformer, outputCollection);
        }
        return outputCollection;
    }

    /**
     * <p>collect.</p>
     *
     * @param inputIterator a {@link java.util.Iterator} object.
     * @param transformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param outputCollection a R object.
     * @param <I> a I object.
     * @param <O> a O object.
     * @return a R object.
     * @param <R> a R object.
     */
    public static <I, O, R extends Collection<? super O>> R collect(final Iterator<? extends I> inputIterator,
                                                                    final Transformer<? super I, ? extends O> transformer, final R outputCollection) {
        if (inputIterator != null && transformer != null) {
            while (inputIterator.hasNext()) {
                final I item = inputIterator.next();
                final O value = transformer.transform(item);
                outputCollection.add(value);
            }
        }
        return outputCollection;
    }

    /**
     * <p>addAll.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param iterable a {@link java.lang.Iterable} object.
     * @param <C> a C object.
     * @return a boolean.
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterable<? extends C> iterable) {
        if (iterable instanceof Collection<?>) {
            return collection.addAll((Collection<? extends C>) iterable);
        }
        return addAll(collection, iterable.iterator());
    }

    /**
     * <p>addAll.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param iterator a {@link java.util.Iterator} object.
     * @param <C> a C object.
     * @return a boolean.
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterator<? extends C> iterator) {
        boolean changed = false;
        while (iterator.hasNext()) {
            changed |= collection.add(iterator.next());
        }
        return changed;
    }

    /**
     * <p>addAll.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param enumeration a {@link java.util.Enumeration} object.
     * @param <C> a C object.
     * @return a boolean.
     */
    public static <C> boolean addAll(final Collection<C> collection, final Enumeration<? extends C> enumeration) {
        boolean changed = false;
        while (enumeration.hasMoreElements()) {
            changed |= collection.add(enumeration.nextElement());
        }
        return changed;
    }

    /**
     * <p>addAll.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param elements an array of C objects.
     * @param <C> a C object.
     * @return a boolean.
     */
    public static <C> boolean addAll(final Collection<C> collection, final C[] elements) {
        boolean changed = false;
        for (final C element : elements) {
            changed |= collection.add(element);
        }
        return changed;
    }

    /**
     * <p>get.</p>
     *
     * @param iterator a {@link java.util.Iterator} object.
     * @param index a int.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T get(final Iterator<T> iterator, final int index) {
        int i = index;
        checkIndexBounds(i);
        while (iterator.hasNext()) {
            i--;
            if (i == -1) {
                return iterator.next();
            }
            iterator.next();
        }
        throw new IndexOutOfBoundsException("Entry does not exist: " + i);
    }

    private static void checkIndexBounds(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
        }
    }

    /**
     * <p>get.</p>
     *
     * @param iterable a {@link java.lang.Iterable} object.
     * @param index a int.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T get(final Iterable<T> iterable, final int index) {
        checkIndexBounds(index);
        if (iterable instanceof List<?>) {
            return ((List<T>) iterable).get(index);
        }
        return get(iterable.iterator(), index);
    }

    /**
     * <p>get.</p>
     *
     * @param object a {@link java.lang.Object} object.
     * @param index a int.
     * @return a {@link java.lang.Object} object.
     */
    public static Object get(final Object object, final int index) {
        int i = index;
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative: " + i);
        }
        if (object instanceof Map<?, ?>) {
            final Map<?, ?> map = (Map<?, ?>) object;
            final Iterator<?> iterator = map.entrySet().iterator();
            return get(iterator, i);
        } else if (object instanceof Object[]) {
            return ((Object[]) object)[i];
        } else if (object instanceof Iterator<?>) {
            final Iterator<?> it = (Iterator<?>) object;
            while (it.hasNext()) {
                i--;
                if (i == -1) {
                    return it.next();
                }
                it.next();
            }
            throw new IndexOutOfBoundsException("Entry does not exist: " + i);
        } else if (object instanceof Collection<?>) {
            final Iterator<?> iterator = ((Collection<?>) object).iterator();
            return get(iterator, i);
        } else if (object instanceof Enumeration<?>) {
            final Enumeration<?> it = (Enumeration<?>) object;
            while (it.hasMoreElements()) {
                i--;
                if (i == -1) {
                    return it.nextElement();
                } else {
                    it.nextElement();
                }
            }
            throw new IndexOutOfBoundsException("Entry does not exist: " + i);
        } else if (object == null) {
            throw new IllegalArgumentException("Unsupported object type: null");
        } else {
            try {
                return Array.get(object, i);
            } catch (final IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    /**
     * <p>get.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param index a int.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map.Entry} object.
     */
    public static <K, V> Map.Entry<K, V> get(final Map<K, V> map, final int index) {
        checkIndexBounds(index);
        return get(map.entrySet(), index);
    }

    /**
     * <p>size.</p>
     *
     * @param object a {@link java.lang.Object} object.
     * @return a int.
     */
    public static int size(final Object object) {
        if (object == null) {
            return 0;
        }
        int total = 0;
        if (object instanceof Map<?, ?>) {
            total = ((Map<?, ?>) object).size();
        } else if (object instanceof Collection<?>) {
            total = ((Collection<?>) object).size();
        } else if (object instanceof Object[]) {
            total = ((Object[]) object).length;
        } else if (object instanceof Iterator<?>) {
            final Iterator<?> it = (Iterator<?>) object;
            while (it.hasNext()) {
                total++;
                it.next();
            }
        } else if (object instanceof Enumeration<?>) {
            final Enumeration<?> it = (Enumeration<?>) object;
            while (it.hasMoreElements()) {
                total++;
                it.nextElement();
            }
        } else {
            try {
                total = Array.getLength(object);
            } catch (final IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
        return total;
    }

    /**
     * <p>sizeIsEmpty.</p>
     *
     * @param object a {@link java.lang.Object} object.
     * @return a boolean.
     */
    public static boolean sizeIsEmpty(final Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length == 0;
        } else if (object instanceof Iterator<?>) {
            return ((Iterator<?>) object).hasNext() == false;
        } else if (object instanceof Enumeration<?>) {
            return ((Enumeration<?>) object).hasMoreElements() == false;
        } else {
            try {
                return Array.getLength(object) == 0;
            } catch (final IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    /**
     * <p>isEmpty.</p>
     *
     * @param coll a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * <p>isNotEmpty.</p>
     *
     * @param coll a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * <p>reverseArray.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     */
    public static void reverseArray(final Object[] array) {
        int i = 0;
        int j = array.length - 1;
        Object tmp;

        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>isFull.</p>
     *
     * @param coll a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public static boolean isFull(final Collection<?> coll) {
        if (coll == null) {
            throw new NullPointerException("The collection must not be null");
        }
        if (coll instanceof BoundedCollection) {
            return ((BoundedCollection<?>) coll).isFull();
        }
        try {
            final BoundedCollection<?> bcoll =
                    UnmodifiableBoundedCollection.unmodifiableBoundedCollection(coll);
            return bcoll.isFull();
        } catch (final IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * <p>maxSize.</p>
     *
     * @param coll a {@link java.util.Collection} object.
     * @return a int.
     */
    public static int maxSize(final Collection<?> coll) {
        if (coll == null) {
            throw new NullPointerException("The collection must not be null");
        }
        if (coll instanceof BoundedCollection) {
            return ((BoundedCollection<?>) coll).maxSize();
        }
        try {
            final BoundedCollection<?> bcoll =
                    UnmodifiableBoundedCollection.unmodifiableBoundedCollection(coll);
            return bcoll.maxSize();
        } catch (final IllegalArgumentException ex) {
            return -1;
        }
    }

    /**
     * <p>collate.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @return a {@link java.util.List} object.
     * @param <O> a O object.
     */
    public static <O extends Comparable<? super O>> List<O> collate(Iterable<? extends O> a,
                                                                    Iterable<? extends O> b) {
        return collate(a, b, ComparatorUtils.naturalComparator(), true);
    }

    /**
     * <p>collate.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param includeDuplicates a boolean.
     * @return a {@link java.util.List} object.
     * @param <O> a O object.
     */
    public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a,
                                                                    final Iterable<? extends O> b,
                                                                    final boolean includeDuplicates) {
        return collate(a, b, ComparatorUtils.naturalComparator(), includeDuplicates);
    }

    /**
     * <p>collate.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param c a {@link java.util.Comparator} object.
     * @param <O> a O object.
     * @return a {@link java.util.List} object.
     */
    public static <O> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b,
                                      final Comparator<? super O> c) {
        return collate(a, b, c, true);
    }

    /**
     * <p>collate.</p>
     *
     * @param a a {@link java.lang.Iterable} object.
     * @param b a {@link java.lang.Iterable} object.
     * @param c a {@link java.util.Comparator} object.
     * @param includeDuplicates a boolean.
     * @param <O> a O object.
     * @return a {@link java.util.List} object.
     */
    public static <O> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b,
                                      final Comparator<? super O> c, final boolean includeDuplicates) {

        if (a == null || b == null) {
            throw new IllegalArgumentException("The collections must not be null");
        }
        if (c == null) {
            throw new IllegalArgumentException("The comparator must not be null");
        }

        // if both Iterables are a Collection, we can estimate the size
        final int totalSize = a instanceof Collection<?> && b instanceof Collection<?> ?
                Math.max(1, ((Collection<?>) a).size() + ((Collection<?>) b).size()) : 10;

        final Iterator<O> iterator = new CollatingIterator<O>(c, a.iterator(), b.iterator());
        if (includeDuplicates) {
            return IteratorUtils.toList(iterator, totalSize);
        } else {
            final ArrayList<O> mergedList = new ArrayList<O>(totalSize);

            O lastItem = null;
            while (iterator.hasNext()) {
                final O item = iterator.next();
                if (lastItem == null || !lastItem.equals(item)) {
                    mergedList.add(item);
                }
                lastItem = item;
            }

            mergedList.trimToSize();
            return mergedList;
        }
    }

    /**
     * <p>permutations.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param <E> a E object.
     * @return a {@link java.util.Collection} object.
     */
    public static <E> Collection<List<E>> permutations(final Collection<E> collection) {
        final PermutationIterator<E> it = new PermutationIterator<E>(collection);
        final Collection<List<E>> result = new LinkedList<List<E>>();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

    /**
     * <p>retainAll.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param retain a {@link java.util.Collection} object.
     * @param <C> a C object.
     * @return a {@link java.util.Collection} object.
     */
    public static <C> Collection<C> retainAll(final Collection<C> collection, final Collection<?> retain) {
        return ListUtils.retainAll(collection, retain);
    }

    /**
     * <p>removeAll.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param remove a {@link java.util.Collection} object.
     * @param <E> a E object.
     * @return a {@link java.util.Collection} object.
     */
    public static <E> Collection<E> removeAll(final Collection<E> collection, final Collection<?> remove) {
        return ListUtils.removeAll(collection, remove);
    }

    /**
     * <p>synchronizedCollection.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param <C> a C object.
     * @return a {@link java.util.Collection} object.
     */
    public static <C> Collection<C> synchronizedCollection(final Collection<C> collection) {
        return SynchronizedCollection.synchronizedCollection(collection);
    }

    /**
     * <p>unmodifiableCollection.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param <C> a C object.
     * @return a {@link java.util.Collection} object.
     */
    public static <C> Collection<C> unmodifiableCollection(final Collection<? extends C> collection) {
        return UnmodifiableCollection.unmodifiableCollection(collection);
    }

    /**
     * <p>predicatedCollection.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param predicate a {@link org.apache.commons.collections4.Predicate} object.
     * @param <C> a C object.
     * @return a {@link java.util.Collection} object.
     */
    public static <C> Collection<C> predicatedCollection(final Collection<C> collection,
                                                         final Predicate<? super C> predicate) {
        return PredicatedCollection.predicatedCollection(collection, predicate);
    }

    /**
     * <p>transformingCollection.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param transformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <E> a E object.
     * @return a {@link java.util.Collection} object.
     */
    public static <E> Collection<E> transformingCollection(final Collection<E> collection,
                                                           final Transformer<? super E, ? extends E> transformer) {
        return TransformedCollection.transformingCollection(collection, transformer);
    }

    /**
     * <p>extractSingleton.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param <E> a E object.
     * @return a E object.
     */
    public static <E> E extractSingleton(final Collection<E> collection) {
        if (collection == null || collection.size() != 1) {
            throw new IllegalArgumentException("Can extract singleton only when collection size == 1");
        }
        return collection.iterator().next();
    }

    private static class CardinalityHelper<O> {
        final Map<O, Integer> cardinalityA;
        final Map<O, Integer> cardinalityB;

        public CardinalityHelper(final Iterable<? extends O> a, final Iterable<? extends O> b) {
            cardinalityA = CollectionUtils.getCardinalityMap(a);
            cardinalityB = CollectionUtils.getCardinalityMap(b);
        }

        public final int max(final Object obj) {
            return Math.max(freqA(obj), freqB(obj));
        }

        public final int min(final Object obj) {
            return Math.min(freqA(obj), freqB(obj));
        }

        public int freqA(final Object obj) {
            return getFreq(obj, cardinalityA);
        }

        public int freqB(final Object obj) {
            return getFreq(obj, cardinalityB);
        }

        private int getFreq(final Object obj, final Map<?, Integer> freqMap) {
            final Integer count = freqMap.get(obj);
            if (count != null) {
                return count.intValue();
            }
            return 0;
        }
    }

    private static class SetOperationCardinalityHelper<O> extends CardinalityHelper<O> implements Iterable<O> {

        private final Set<O> elements;
        private final List<O> newList;

        public SetOperationCardinalityHelper(final Iterable<? extends O> a, final Iterable<? extends O> b) {
            super(a, b);
            elements = new HashSet<O>();
            addAll(elements, a);
            addAll(elements, b);
            newList = new ArrayList<O>(elements.size());
        }

        public Iterator<O> iterator() {
            return elements.iterator();
        }

        public void setCardinality(final O obj, final int count) {
            for (int i = 0; i < count; i++) {
                newList.add(obj);
            }
        }

        public Collection<O> list() {
            return newList;
        }

    }

    private static class EquatorWrapper<O> {
        private final Equator<O> equator;
        private final O object;

        public EquatorWrapper(final Equator<O> equator, final O object) {
            this.equator = equator;
            this.object = object;
        }

        public O getObject() {
            return object;
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof EquatorWrapper)) {
                return false;
            }
            @SuppressWarnings("unchecked") final EquatorWrapper<O> otherObj = (EquatorWrapper<O>) obj;
            return equator.equate(object, otherObj.getObject());
        }

        @Override
        public int hashCode() {
            return equator.hash(object);
        }
    }
}
