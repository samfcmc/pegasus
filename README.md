qa_fenix
========

QA system using Fenix API

To make it work with fenix api you need to follow this steps:
- Go to conf dir
<br>
<code>cd conf</code>
<br>
- Copy the existing fenixedu-sample.yml to a new one called fenixedu.yml
<br>
<code>cp fenixedu-sample.yml fenixedu.yml</code>
<br>
- Fill each property according to application details in fenix external applications system
- After saving the file, you can run as usual
<br>
<code>play run</code>
<br>

Configure database
<br>
Now, the application just supports mysql
<br>
- Run your mysql server
<br>
- Create a new empty database
<br>
- Create the following environment variables
<br>
<code> QA_DB_URL=jdbc:mysql://localhost/<db name>/code>
<code> QA_DB_USER=<user for your db></code>
<code> QA_DB_PASS=<password for your db></code>
<br>

Enjoy ;)
