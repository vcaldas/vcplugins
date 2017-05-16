Introduction
============
.. _intro:

he State of Python (3 & 2)
~~~~~~~~~~~~~~~~~~~~~~~~~~~

When choosing a Python interpreter, one looming question is always present:
"Should I choose Python 2 or Python 3"? The answer is a bit more subtle than
one might think.


The basic gist of the state of things is as follows:

1. Most production applications today use Python 2.7.
2. Python 3 is ready for the production deployment of applications today.
3. Python 2.7 will only receive necessary security updates until 2020 [#pep373_eol]_.
4. The brand name "Python" encapsulates both Python 3 and Python 2.

Recommendations
~~~~~~~~~~~~~~~

I'll be blunt:

- Use Python 3 for new Python applications.
- If you're learning Python for the first time, familiarizing yourself with Python 2.7 will be very
  useful, but not more useful than learning Python 3.
- Learn both. They are both "Python".
- Software that is already built often depends on Python 2.7.
- If you are writing a new open source Python library, it's best to write it for both Python 2 and 3
  simultaneously. Only supporting Python 3 for a new library you want to be widely adopted is a
  political statement and will alienate many of your users. This is not a problem — slowly, over the next three years, this will become less the case.

So.... 3?
~~~~~~~~~
