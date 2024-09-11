package info.galudisu

import info.galudisu.Tree.Color.B
import info.galudisu.Tree.Color.R
import kotlin.math.max

/**
 * The red-black tree is a binary search tree with some additions to its structure and a modified insertion algorithm that also balances the result. Unfortunately, Okasaki didn't describe removal, which happens to be a far more complex process. But in 2014, authors Germane and Might described this missing method.
 *
 * Ina red-black tree, each tree (including subtrees) has an additional property representing its color. Note that it could be any color or even any property representing a binary choice. Besides this, the structure is exactly the same as the binary tree structure, as shown in the following listing.
 */
sealed class Tree<out A : Comparable<@UnsafeVariance A>> {
  abstract val size: Int

  abstract val height: Int

  internal abstract val color: Color

  // The isTR and isTB functions test whether
  // a tree is nonempty and red or nonempty
  // and black, respectively.

  internal abstract val isTB: Boolean

  internal abstract val isTR: Boolean

  internal abstract val right: Tree<A>

  internal abstract val left: Tree<A>

  internal abstract val value: A

  internal abstract class Empty<out A : Comparable<@UnsafeVariance A>> : Tree<A>() {
    override val size: Int = 0

    override val height: Int = -1

    /** The empty tree is black. */
    override val color: Color = B

    override val isTB: Boolean = false

    override val isTR: Boolean = false

    // Properties that make no sense in the Empty class are lazily throwing exceptions.

    override val right: Tree<Nothing> by lazy {
      throw IllegalStateException("right called on Empty tree")
    }

    override val left: Tree<Nothing> by lazy {
      throw IllegalStateException("left called on Empty tree")
    }

    override val value: Nothing by lazy {
      throw IllegalStateException("value called on Empty tree")
    }
  }

  /**
   * The empty tree is represented by the E singleton.
   */
  internal object E : Empty<Nothing>()

  /**
   * @param color Nonempty trees can be black or red.
   */
  internal class T<out A : Comparable<@UnsafeVariance A>>(
    override val color: Color,
    override val left: Tree<A>,
    override val value: A,
    override val right: Tree<A>,
  ) : Tree<A>() {
    override val size: Int = left.size + 1 + right.size

    override val height: Int = max(left.height, right.height) + 1

    override val isTB: Boolean = color == B

    override val isTR: Boolean = color == R
  }

  companion object {
    /**
     * This function returns an empty tree.
     */
    operator fun <A : Comparable<A>> invoke(): Tree<A> = E
  }

  /**
   * Colors are singleton objects.
   */
  sealed class Color {
    // Red
    internal data object R : Color()

    // Black
    internal data object B : Color()
  }
}
