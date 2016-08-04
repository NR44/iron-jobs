[![Build Status](https://travis-ci.org/NR44/iron-jobs.svg?branch=master)](https://travis-ci.org/NR44/iron-jobs)

#Iron Jobs

![futurama](futurama.jpg)

## Description
This is your first time working in a group officially.  You will be working with 1 or 2 other people on a job board.  As the backend group you are responsible for making the REST api required to power this site.  The front end will make the site run.

## Details
This is a very basic job board site.  A user will be able to login/register.  They can then look for jobs or post a job of their own.  If they apply the user will be linked to the posting.

## Requirements
* Create a `User` class that has/can do the following:
	* Username
	* Password
	* email address
	* Token for API
	* location: City/State
* Create a `Posting` class that has the following:
	* Owner (relationship to the `User`)
	* Title
	* Description
	* Salary Range Start
	* Salary Range End
	* Date created
	* Location
	* Applicants (ManyToMany relationship to `User`)
* End Points (minimum but not all inclusive)
	* login and return a token or register a new user and return a token
	* Retrieve the current user for the token provided
	* Retrieve all postings for the user
	* Retrieve all postings applied for
	* List all postings (filtering and sorting via get params)
		* Reverse choronological order
		* Option to filter by salary and location
		* Option to sort by salary
	* Create new job posting
	* Get a job posting detail (return a single job posting object)
	* Apply to job posting
* Write tests for the end points
* Use Travis-CI to test the repository
* Deploy your application to Heroku so make it available for your front end person

## Larger Groups
Travis-CI
* Make your deploys through travis-ci 
* It should run tests
* Deploy to heroku

Messaging system:
* Applicant can send a message to posting owner
* Posting owner can reply to message (Similar to our forum web example project)
* Message class should have the following:
	* Date created
	* Owner
	* Original message if replying to another message
	* Posting relationship to keep it with the job

## Hard Mode
* Complete messaging system for larger groups.
* Notification system.  Allow the user to see a list of activity which includes:
	* New jobs
	* New messages
	* Custom messages for admins to send

## Resources
* [Github Repo](https://github.com/tiy-lv-java-2016-06/iron-jobs)
* [Travis-ci](https://travis-ci.org/)## Synopsis
This is a job board where the user can login & register. Jobs from a certain location will be displayed. User can filter jobs based on salary range.
