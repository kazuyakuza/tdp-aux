note
	description : "proyecto3 application root class"
	date        : "$Date$"
	revision    : "$Revision$"

class
	MIRAGE

inherit
	ARGUMENTS

create
	make

feature -- Initialization

	make
			-- Run application.
		do
			create modulo.make;
		end

feature {NONE} -- Access
	modulo: MC72

feature -- Access
	Atacar_Objetivo(objetivo: STRING) is
		--
		local
			bandera: INTEGER
			intentos: INTEGER
			fallo: BOOLEAN
			reintentar: BOOLEAN
		do
			if (not fallo) then
				modulo.Preparar_Misil;
				bandera := 1;
				modulo.Apuntar_Misil(objetivo);
				bandera := 2;
				modulo.Lanzar_Misil;
			end
		rescue
			if (bandera = 0) then
				-- fallo Preparar_Misil
				fallo := true;
				reintentar := true;
			elseif (bandera = 1) then
				-- fallo Apuntar_Misil
				intentos := intentos +1;
				reintentar := intentos < 5;
			else
				-- bandera = 2
				-- fallo Lanzar_Misil
				reintentar := false;
			end
			if (reintentar) then
				bandera := 0; --Reiniciar bandera
				retry;
			end
		end

end
