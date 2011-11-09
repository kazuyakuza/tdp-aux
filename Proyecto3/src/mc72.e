note
	description: "Summary description for {MC72}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	MC72

create
	make

feature -- Initialization

	make
			-- Initialization for `Current'.
		do

		end

feature {NONE} -- Access
	misil : MISIL

feature {MC72} -- Access
	Hay_Misiles: BOOLEAN is
			--
			local
				r : RANDOM
				t : DATE_TIME
			do
				create r.make;
				create t.make_now;
				r.set_seed (t.seconds);
				result := (r.next_random (t.seconds) \\ 2) = 0;
			end

feature {MC72} -- Access
	Setup_Misil is
			--			
			do
				if (Hay_Misiles()) then
					create misil.make;
				else --fallar
					generar_Falla();
				end
			end

feature {MC72} -- Access
	Cargar_Misil is
			--
			--require
			--	Hay_Misil: misil /= Void;
			do
				misil.Activar;
			end

feature {NONE}-- Access
	generar_Falla is
			--
			local
				n : INTEGER
			do
				n := n.integer_quotient (0);
			end

feature -- Access
	Preparar_Misil is
			--
			local
			bandera : INTEGER
			do
				Current.Setup_Misil;
				bandera := bandera +1;
				Current.Cargar_Misil;
			rescue
				if (bandera = 1) then
					-- Falló Cargar_Misil, reintentar.
					retry
				end
				-- Falló Setup_Misil, fallar.
			end

feature -- Access
	Apuntar_Misil (objetivo: STRING) is
			--
			require

			do
				misil.Fijar_Blanco(objetivo);
			end

feature -- Access
	Lanzar_Misil is
			--
			do
				misil.Despegar;
			end


end
