# Ingress Jobs Backend System

Ingress Jobs is a backend job portal designed to manage job listings. It allows job seekers to search, filter, and view job opportunities, while employers can create and manage job posts.

## Core Features:
- **Job Listings**: Create, view, and manage job positions with detailed fields.
- **Search & Filters**: Filter jobs by location, job type, experience level, and more.
- **Batch Job Loading**: Scrape jobs from Djinni and filter based on specific criteria.


## Data Scraping:
- Fetch jobs from Djinni for the last 3 months.

## Non-Functional Requirements:
- **Logging**: Implement proper logging.
- **Scalability**: Ensure the app can scale with increased load.
- **Documentation**: Provide a simple user guide.

### Suggested Tech Stack:
- **Backend**: Java (Spring Boot)
- **Database**: PostgreSQL
- **Scraping**: JSoup
- **Containerization**: Docker
