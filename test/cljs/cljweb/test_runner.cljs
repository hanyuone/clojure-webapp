(ns cljweb.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [cljweb.core-test]
   [cljweb.common-test]))

(enable-console-print!)

(doo-tests 'cljweb.core-test
           'cljweb.common-test)
