@author:        Oshane Bailey
@id:            620042528
@project nane:  Dictionary Attack

@Demo:          https://www.youtube.com/watch?v=yqCx9HLmJqQ
@git repo:      https://github.com/b4oshany/Hijacking-Login-Demo/commits?author=b4oshany

Description:
-------------------------------- User Login ---------------------------------------------------------
This is an Java application that simulates password creation for a userid on register, and handles
authentication attempts. Passwords that are generated are hashed with the sha1 hashing algorithm and stored in a text file
with the associated userid and status, "assest/data/dictionarylist*-*-*.txt" (the * is are wildcards which represent numbers; minimum and maximum character length and number of words to try).
Format of this password database file is:
userid1,passwordhash1,status1
userid2,passwordhash2,status2
userid3,passwordhash3,status3

Passwords set should have a minimum length of 8 characters.
- For authentication mode, the application must prompt to enter userid and password.
- When userid and password are entered, the password given at the prompt is to be hashed with sha1
and compared with the hash associated with the given userid.
- If the hashes do not match, the application is to indicate an error message, but if they match, a success
message is to be given.
- If the userid does not exist in the password database file, the application is to indicate an error
message.
- All password creation, successful and failed authentication attempts should be logged in a text based
log file.
- After 3 successive failed password entries, the userid for which authentication is being attempted,
should have status set to “locked” ie. no further authentication attempts should be allowed for that
account until it status is set to “unlocked”.

To login or register, run the UserAccountForm main function, where you will be provided with a login form. If you click register then it will give you a registration form.
Once you register, click login, inorder to login with that user.

--------------------------- Hacking Login -----------------------------------------------------------------------

The part of the application is run by, running the HackUserAccountForm mainn function. Once it is runned, it will call the main function of the User Login, and provide its own form, where
you should enter the userid, minimum (default to 8) and maximum character (default to 16) length and number of words to try (default 2000).

This process, autogenerate passwords between the given charcher length, the amount of passwords to be generate by the number of attempts. These passwords are then stored in a text file and passed to the dictionary attacker function, which
fills in the login form of the UserAccountForm Jframe, and perform a doClick function on the login button. Once there is a login, or change of status screen then the password has been stored

---------------------------- Assumptions --------------------------------------------------------------------------

Attack Success Percentage.
Considering that there wil be x characters, and it password is alphanumeric, then the permutation and the maximum tries should be 36^x, for instance, a password of 8 character length has 38^8 possibility, which result in less than 0.5% probability of getting that password.

Measure of Avoiding this type of Attack.
Generate an access token after the numbers of tries been invalidated. Afterwards, the user will be blocked from the login form or any type of login. An email or so can then be sent to the user with that access token to access the login form once again.
If the hacker is aware of the location of the users.ser, user and password database, which is in plain text, then we can simply encrypt the text file and set effective read write permissions to the application.

Hacking Improvement.
Not much can be done considering that the password is autogenerate with an random alphanumeric value. However, what can be done with application is, implement threading whereby, the dictionary list is splits in parts and each spawned thread will take portions of the list and test it.
This will increase the time taken to crack the user password but it will take a toll on the host system.

Weakness of Dictionary Attack.
- The UserAccountForm, will be aware of the number of the vast number of attempts by that user and take additional measure to block the user.
- Its very slow to crack a user password

Password Security Improvementt.
Not much can be done considering that the password is autogenerate with an random alphanumeric value and the fact that the application is directly interacting with the login form. However, the autogenerate password can be increased in character length which lower the probability of it been acked.

