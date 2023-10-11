//https://introjs.com/docs/
Page.onReady = function() {};

Page.primerTourClick = function($event, widget) {
    let steps = [{
        title: 'App Tour',
        intro: 'Esta aplicación esta pensada para explicar como utilizar la librería IntroJs.'
    }, {
        title: 'Tabla principal',
        element: document.getElementById("tablaPrincipal"),
        intro: "Aqui se puede observar la tabla de clientes"
    }, {
        title: 'Leftnav',
        element: document.getElementById("leftnav"),
        intro: "Leftnav"
    }, {
        title: 'Dashboard',
        element: document.getElementById("Dashboard"),
        intro: "Dash"
    }, {
        title: 'Pending',
        element: document.getElementById("Pending"),
        intro: "Pending"
    }];
    let tour = introJs().setOptions({
        steps: steps
    }).start();
    tour.onbeforechange(function(targetElement) {
        if (this._currentStep == 2) {
            // Do something
        }
    });
};

Page.dontShowClick = function($event, widget) {
    let steps = [{
        title: 'No mostrar',
        intro: 'Si seleccionas el checkbox no se volvera a mostrar'
    }];
    let tour = introJs().setOptions({
        "dontShowAgain": true,
        steps: steps
    }).start();
};


Page.htmlTourClick = function($event, widget) {
    let steps = [{
        title: '<h1>HTML</h1>',
        element: document.getElementById("tablaPrincipal"),
        intro: '<h3>Se pueden insertar etiquetas html</h3>'
    }, {
        title: 'Imagenes',
        intro: '<img src="resources/images/logos/2022-07-21_12-32.png" />'
    }, {
        title: 'Listas',
        intro: `<ol>
  <li>Julio</li>
  <li>Carmen</li>
  <li>Ignacio</li>
  <li>Elena</li>
</ol>`
    }];
    let tour = introJs().setOptions({
        steps: steps
    }).start();
};
