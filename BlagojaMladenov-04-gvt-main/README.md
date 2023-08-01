<h2>Great Versioning Tool (gvt)</h2>

<p>You need to write a file version control system - a (very) simplified GIT.</p>

<p>The system only handles files. It does not handle subdirectories and does not need to (but can) handle links.</p>

<p><strong>NOTE!</strong> Please use the <code>ExitHandler</code> class to terminate the program instead of directly calling <code>System.exit</code>!</p>

<p>The basic unit of operation is a <em>version</em>. Each <em>version</em> contains:</p>
<ul>
<li>version number (from <code>0</code> to <code>Long.MAX_VALUE</code>);</li>
<li>commit message added during the version's commit (<code>commit</code>);</li>
<li>all files that were added (<code>add</code>) to gvt. Files that are committed in a specific version cannot be modified within that version - committing their modifications means creating a new version.</li>
<li><em>last version</em> is the version that was most recently created. New versions can be created using the following commands: <code>init</code> (only for version 0), <code>add</code>, <code>detach</code>, <code>commit</code>.</li>
</ul>

<h3>Running the Application</h3>
<ul>
<li>The system should provide an executable class called <code>Gvt</code>. This class will be used to execute all commands.</li>
<li>The command will always be the first parameter of the program execution.</li>
<li>If no parameters are provided, the program should print to <code>System.out</code>: <code>Please specify command.</code>, and return an error code of 1.</li>
<li>If an unknown command is specified, the program should print to <code>System.out</code>: <code>Unknown command {specified-command}.</code>, and return an error code of 1.</li>
</ul>

<h3>General Rules</h3>
<ul>
<li>All commands (except <code>init</code>) should only work in an initialized directory. If the current directory is not initialized, all other commands should print to <code>System.out</code>: <code>Current directory is not initialized. Please use "init" command to initialize.</code>, and return an error code of -2.</li>
<li>If there is a system-level error (e.g., lack of disk space, lack of write permissions), print to <code>System.out</code>: <code>Underlying system problem. See ERR for details.</code>, return an error code of -3, and print the stack trace to <code>System.err</code>.</li>
</ul>

<h3>Rules for commands: add, detach, commit</h3>
<ul>
<li>All these commands can take an optional parameter, <code>-m {"Message in double quotes"}</code>, which can be specified as the last parameter. This is the <em>user message</em>. It should be appended to the <em>default message</em> to form the version message.</li>
</ul>

<h3>Commands</h3>
<h4>init</h4>
<p>Inicjalizuje system gvt w bieżącym katalogu, oraz ustawia aktywną i ostanią wersję na 0. Wiadomość do wersji 0: <code>GVT initialized.</code></p>

<ul>
<li>If the directory is already initialized, print to <code>System.out</code>: <code>Current directory is already initialized.</code>, and return an error code of 10.</li>
<li>If initialization is successful, print to <code>System.out</code>: <code>Current directory initialized successfully.</code>.</li>
</ul>

<p>During initialization, Gvt should create a directory named <code>.gvt</code>. Inside this directory, the system can store all the necessary data for its operation.</p>

<h4>add</h4>
<p>Adds the file specified as a parameter to this command. It also accepts an optional user message (as described in the general rules).</p>

<ul>
<li>If no file is specified, print to <code>System.out</code>: <code>Please specify file to add.</code>, and return an error code of 20.</li>
<li>If a file is specified, the following should be done:
<ul>
<li>If successful, print to <code>System.out</code>: <code>File added successfully. File: {file-name}</code>, and create a new version.</li>
<li>If the file does not exist, print to <code>System.out</code>: <code>File not found. File: {file-name}</code>, and return an error code of 21.</li>
<li>If the file is already added, print to <code>System.out</code>: <code>File already added. File: {file-name}</code>.</li>
<li>If any other error occurs, print to <code>System.out</code>: <code>File cannot be added. See ERR for details. File: {file-name}</code>, print the stack trace to <code>System.err</code>, and return an error code of 22.</li>
</ul>
</li>
</ul>

<p>Default message: <code>Added file: {file-name}</code>.</p>

<h4>detach</h4>
<p>Detaches (but does not delete from the file system!) the file specified as a parameter from gvt. It also accepts an optional user message (as described in the general rules).</p>

<ul>
<li>If no file is specified, print to <code>System.out</code>: <code>Please specify file to detach.</code>, and return an error code of 30.</li>
<li>If a file is specified, the following should be done:
<ul>
<li>If successful, print to <code>System.out</code>: <code>File detached successfully. File: {file-name}</code>, and create a new version.</li>
<li>If the file is not added to gvt, print to <code>System.out</code>: <code>File is not added to gvt. File: {file-name}</code>.</li>
<li>If any other error occurs, print to <code>System.out</code>: <code>File cannot be detached, see ERR for details. File: {file-name}</code>, print the stack trace to <code>System.err</code>, and return an error code of 31.</li>
</ul>
</li>
</ul>

<p>Default message: <code>File detached successfully. File: {file-name}</code>.</p>

<h4>checkout</h4>
<p>Restores files to the state from a specific version specified in the parameter.</p>

<p>The command does not change the state of file control by GVT. For example, if a file was controlled in the restored version and is not controlled in the last version, it should NOT be added to GVT; only its contents should be restored (or recreated if it was deleted in the meantime). Files that are not controlled in both versions remain unchanged.</p>

<p>Accepts 1 parameter: the version number to restore.</p>

<ul>
<li>If the specified version is invalid (does not exist or is not a number), print to <code>System.out</code>: <code>Invalid version number: {specified-number}.</code>, and return an error code of 40.</li>
<li>If the specified version is valid, restore the state of all files to the state from the specified version, and print to <code>System.out</code>: <code>Version {n} checked out successfully.</code>.</li>
<li>For other errors, follow the general rules.</li>
</ul>

<h4>commit</h4>
<p>Creates a new version in GVT with the file specified as a parameter. It also accepts an optional user message (as described in the general rules).</p>

<ul>
<li>If no file is specified, print to <code>System.out</code>: <code>Please specify file to commit.</code>, and return an error code of 50.</li>
<li>If a file is specified, the following should be done:
<ul>
<li>If the file was added and still exists, create a new version, print to <code>System.out</code>: <code>File committed successfully. File: {file-name}</code>.</li>
<li>If the file was not added, print to <code>System.out</code>: <code>File is not added to gvt. File: {file-name}</code>.</li>
<li>If the file does not exist, print to <code>System.out</code>: <code>File not found. File: {file-name}</code>, and return an error code of 51.</li>
<li>If any other error occurs, print to <code>System.out</code>: <code>File cannot be committed, see ERR for details. File: {file-name}</code>, print the stack trace to <code>System.err</code>, and return an error code of 52.</li>
</ul>
</li>
</ul>

<p>Default message: <code>Committed file: {file-name}</code>.</p>

<h4>history</h4>
<p>Displays the version history.</p>

<p>Format: <code>{version-number}: {commit message}</code>. Each version is displayed on a new line. If the commit message is multi-line, only the first line should be displayed.</p>

<ul>
<li>If no parameters are specified, display all versions.</li>
<li>Parameter <code>-last {n}</code>: display the last n versions.</li>
<li>Incorrect parameters should be ignored and treated as no parameters.</li>
</ul>

<h4>version</h4>
<p>Displays version details for the number specified as a parameter.</p>

<ul>
<li>If no parameter is specified, display the last version.</li>
<li>If the specified version is invalid (does not exist or is not a number), print to <code>System.out</code>: <code>Invalid version number: {specified-number}.</code>, and return an error code of 60.</li>
</ul>

<p>Format:</p>
<pre>
Version: {version-number}
{commit message}
</pre>
