<h2>Task 1: Merging Lists.</h2>
<p>You need to implement the <code>uj.wmii.pwj.collections.ListMerger.mergeLists</code> method in such a way that it creates a new immutable list containing elements from the first and second lists alternately. The method should never return null, but an empty list instead.</p>

<h2>Task 2: Generating a JSON String from a Collection.</h2>
<p>You need to build a class that implements the <code>uj.wmii.pwj.collections.JsonMapper</code> interface to create a JSON-formatted String from a provided map.</p>

<p>The <code>defaultInstance</code> method in the <code>JsonMapper</code> interface serves as a factory method - it should return an instance of the created class.</p>

<p>Allowed types of values:</p>
<ul>
  <li><code>String</code> - should be converted to a JSON string.</li>
  <li><code>int</code>, <code>short</code>, <code>long</code>, <code>byte</code>, <code>boolean</code>, <code>float</code>, <code>double</code> - should be converted to the corresponding JSON types (numeric, boolean).</li>
  <li><code>Map</code> - should be converted to a nested object.</li>
  <li><code>List</code> - should be converted to an array.</li>
</ul>

<h2>Task 3: Building a Random Battleship Game Map Generator.</h2>
<p>You need to write a generator to randomly create valid battleship game maps (<code>uj.wmii.pwj.collections.collections.BattleshipGenerator.generateMap</code>).</p>

<p>The <code>defaultInstance</code> method in the <code>BattleshipGenerator</code> interface serves as a factory method - it should return an instance of the created class.</p>

<p>The battleship game map is a 10x10 square (the API should return a <code>String</code> with a size of 100, indexes 0-9: the first row, 10-19 the second row, and so on). Each cell can contain a ship element (mast) marked with <code>*</code>, or water marked by <code>.</code>.</p>

<p>Ships can be 1, 2, 3, or 4 masts long. A ship consists of one or more adjacent cells containing a mast. Masts touching only by corners are not considered part of the same ship.</p>

<p>Examples of valid ships (surrounded by water):</p>
<pre>
...
.#.  - one-mast ship
...

......
.##.#. - two two-mast ships
....#.

.....
..#..  - three-mast ship
.##..

.........
......##.
.####.##. - two four-mast ships
.........
</pre>

<p>Examples of invalid ships:</p>
<pre>
......
..#...  - invalid two-mast ship
...#..
......
.......
...#...
..#.#..  - invalid four-mast ship
...#...
</pre>

<p>A valid map contains: 4 one-mast ships, 3 two-mast ships, 2 three-mast ships, and 1 four-mast ship. There must be at least one space between ships (ships cannot touch each other by corners).</p>

<p>Example of a valid map:</p>
<pre>
..#.......#......#..#..#........##............##...##................#..##...#...##....#.#.......#..
</pre>

<p>The same map with additional line breaks every 10 characters for readability:</p>
<pre>
..#.......
#......#..
#..#......
..##......
......##..
.##.......
.........#
..##...#..
.##....#.#
.......#..
</pre>