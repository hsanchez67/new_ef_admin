require({
    paths: {
      underscore: 'libs/underscore-min',
      backbone: 'libs/backbone-min',
      moduleActivator: 'libs/module-activator'
    }
  }, [
      'underscore',
      'moduleActivator',
      'backbone'
  ], function(underscore,moduleActivator,backbone){
	  moduleActivator.execute();
  }
);
