<h1>09-delegations-calculator</h1>
    <p>The task is to implement a calculator for the amount due for a business trip: class `Calc`, method `calculate`.</p>
    <p>Test data is located in the file `src/test/resources/delegations.csv`.</p>
    <h2>Parameters:</h2>
    <ul>
        <li>start: the start date of the business trip in the format `yyyy-mm-dd HH:MM timezone`</li>
        <li>end: the end date of the business trip in the format `yyyy-mm-dd HH:MM timezone`</li>
        <li>dailyRate: the daily rate for the business trip</li>
    </ul>
    <p>The test data also includes an additional column named `expected`, which contains the expected result of the calculations.</p>
    <h2>Rules for calculating the business trip:</h2>
    <ul>
        <li>For a full day: the full daily rate is due</li>
        <li>For up to 8 hours: 1/3 of the daily rate is due</li>
        <li>For more than 8 up to 12 hours: 1/2 of the daily rate is due</li>
        <li>For more than 12 hours: the full daily rate is due</li>
        <li>If the start time is later or the same as the end time, no amount is due.</li>
    </ul>