# AMS Enterprise Security Audit Request

This pull request exists only for AI security auditing.

The system is a production Academic Management System.

Architecture:
Controller → Service → Mapper → Database

Security features already implemented:

- role-based authorization
- file upload validation
- download authorization
- database constraints
- production hardened deployment

Audit Goals:

1. Security vulnerabilities
   - IDOR
   - authorization bypass
   - file upload vulnerabilities
   - path traversal
   - unsafe deserialization

2. Backend logic vulnerabilities
   - race conditions
   - transaction boundaries
   - improper exception handling

3. Architecture review
   - controller/service separation
   - transaction placement
   - service responsibility

4. Database safety
   - missing constraints
   - potential data corruption
   - concurrency issues

5. Enterprise readiness
   - observability
   - error handling
   - logging
   - monitoring readiness

Important:

This system follows strict service-centric architecture.

Avoid suggesting changes that break this architecture.
