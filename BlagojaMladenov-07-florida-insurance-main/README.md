<h2>Task for Lecture 7: Florida Insurance</h2>

<p>The file <code>FL_insurance.csv.zip</code> contains data on insurance in Florida for the years 2011-2012. It contains a considerable amount of data, so the file is compressed.</p>

<p>Using the API for file reading, you need to read the data from this file (I recommend using <code>ZipFile</code>) and convert it into a list containing this data in memory.</p>

<p>Using stream processing, you should perform the following operations:</p>

<ul>
  <li>Generate a file named <code>count.txt</code>, which will contain the number of unique countries in the data ("country" column).</li>
  <li>Generate a file named <code>tiv2012.txt</code>, which will contain the sum of insurance values for all properties for the year 2012 (column "tiv_2012").</li>
  <li>Generate a file named <code>most_valuable.txt</code>, containing 2 columns: "country" and "value". The "country" column should contain the names of the top 10 countries for which the total insurance value increased the most between 2011 and 2012; the names should be sorted in descending order based on the increase in value. The "value" column should contain the value of this increase for each country, rounded to 2 decimal places (5 rounded up; 2 decimal places always, even if it's 0).</li>
</ul>

<p>The files should be generated in the main project directory. All files should include a header row. Column separator: comma <code>,</code>. Decimal separator: period <code>.</code></p>

<p>NOTE: Please remember that "hardcoding" the results, despite passing the tests, does not mean the task is completed. To receive a grade of BDB (Very Good), each operation should be handled in one stream pass.</p>
