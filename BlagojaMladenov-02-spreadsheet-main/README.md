<h2>Lecture 2 Tasks.</h2>

<h3>Task 1: Exercises</h3>
<ol>
  <li>Determine the maximum number of dimensions a Java array can have. What happens if this limit is exceeded?</li>
  <li>Explain the difference between a static inner class and a non-static inner class (nested class). Provide an example to demonstrate the difference.</li>
  <li>Implement the "diamond problem" and demonstrate how to resolve it.</li>
</ol>

<h3>Task 2: Spreadsheet</h3>
<p>The task is to implement calculations in a simple spreadsheet. The spreadsheet operates only on integers within the range of the <code>int</code> type.</p>

<p>In the <code>uj.pwj2020.spreadsheet.Spreadsheet</code> class, you need to implement the <code>calculate</code> method. It should take the source spreadsheet (passed as a parameter) and return the resulting spreadsheet after performing the calculations. The input array contains rows and columns sequentially.</p>

<p>The spreadsheet should support the following operations:</p>
<ul>
  <li>Value: If a cell contains a number, it should be left as is.</li>
  <li>Reference: If a cell starts with the <code>$</code> sign, it contains a reference to another cell. References are similar to Excel's format, for example: <code>$A1</code> denotes the first column (<code>A</code>) and the first row (<code>1</code>). <code>$C7</code> denotes the third column and the seventh row.</li>
  <li>Formula: If a cell starts with the <code>=</code> sign, it contains a formula. Each formula consists of the <code>=</code> sign, the formula name, and two parameters separated by a comma and enclosed in regular parentheses. The formula's parameters can only be a value or a reference (but not another formula).</li>
</ul>

<p>Existing formulas:</p>
<ul>
  <li><code>ADD</code>: Adds both parameters.</li>
  <li><code>SUB</code>: Subtracts the second parameter from the first.</li>
  <li><code>MUL</code>: Multiplies both parameters.</li>
  <li><code>DIV</code>: Divides the first parameter by the second parameter, using integer division.</li>
  <li><code>MOD</code>: Calculates the remainder of dividing the first parameter by the second parameter.</li>
</ul>

<p>The spreadsheet will not contain cyclic references (e.g., cell <code>A1</code> refers to <code>B2</code>, and <code>B2</code> refers back to <code>A1</code>).</p>

<p>Example:</p>
<pre>
For the spreadsheet:
1,2,3
4,5,6
$A1,$C1,$B3
=ADD(10,$A1),=SUB($C3,$A1),0
</pre>

<p>The result should be:</p>
<pre>
1,2,3
4,5,6
1,3,3
11,2,0
</pre>
